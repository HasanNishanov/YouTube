package com.company.service;

import com.company.dto.attach.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.exp.ItemNotFoundException;
import com.company.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AttachService {

    @Value("${attach.folder}")
    private String attachFolder;

    @Value("${server.url}")
    private String serverUrl;
    //    @Autowired
//    private ProfileService profileService;
//    @Autowired
//    private ArticleService articleService;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    @Lazy
    private ProfileService profileService;

    public AttachDTO saveToSystem(MultipartFile file) {

        ProfileEntity entity = profileService.getProfile();

        try {

            File folder = new File(attachFolder + getYmDString());

            AttachEntity attach = new AttachEntity();
//            split[0], split[1], file.getSize(), folder.getPath(), article
            attach.setExtension(getExtension(file.getOriginalFilename()));
            attach.setOriginalName(file.getOriginalFilename()
                    .replace(("." + getExtension(file.getOriginalFilename())), ""));
            attach.setSize(file.getSize());
            attach.setPath(getYmDString());
            attachRepository.save(attach);

            String fileName = attach.getUuid() + "." + getExtension(file.getOriginalFilename());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder + getYmDString() + "/" + fileName);
            Files.write(path, bytes);

            AttachDTO dto = new AttachDTO();
            dto.setUrl(serverUrl + "attach/open?fileId=" + attach.getUuid());
            return dto;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AttachDTO saveToSystemForProfile(MultipartFile file) {

        ProfileEntity profile = profileService.getProfile();

        try {

            File folder = new File(attachFolder + getYmDString());

            AttachEntity attach = new AttachEntity();
//            split[0], split[1], file.getSize(), folder.getPath(), article
            attach.setExtension(getExtension(file.getOriginalFilename()));
            attach.setOriginalName(file.getOriginalFilename()
                    .replace(("." + getExtension(file.getOriginalFilename())), ""));
            attach.setSize(file.getSize());
            attach.setPath(getYmDString());
            attachRepository.save(attach);

            if (profile.getPhoto() != null) {
                Files.delete(Path.of(attachFolder + profile.getPhoto().getPath() + "/" + profile.getPhoto().getUuid() + "." + profile.getPhoto().getExtension()));
            }

            profile.setPhoto(attach);
            profileService.save(profile);

            String fileName = attach.getUuid() + "." + getExtension(file.getOriginalFilename());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder + getYmDString() + "/" + fileName);
            Files.write(path, bytes);

            AttachDTO dto = new AttachDTO();
            dto.setUrl(serverUrl + "attach/open?fileId=" + attach.getUuid());
            return dto;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] loadImage(String fileName) {
        byte[] imageInByte;

        fileName = getFolderPathFromUrl(fileName);
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File("attaches/" + fileName));
        } catch (Exception e) {
            return new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(originalImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageInByte;
    }

    public byte[] openGeneral(String fileUUID) {

        AttachEntity attach = get(fileUUID);

        byte[] data;
        try {
            // fileName -> zari.jpg
            String path = "attaches/" + attach.getPath() + "/" + fileUUID + "." + attach.getExtension();
            Path file = Paths.get(path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public Resource download(String fileUUID) {

        AttachEntity attach = get(fileUUID);
        try {

            String path = "attaches/" + attach.getPath() + "/" + fileUUID + "." + attach.getExtension();
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getFolderPathFromUrl(String url) { // 2022_6_20_f978a682-a357-4eaf-ac18-ec9482a4e58b.jpg
        String[] arr = url.split("_");
        return arr[0] + "/" + arr[1] + "/" + arr[2] + "/" + arr[3];
        // 2022/06/20/f978a682-a357-4eaf-ac18-ec9482a4e58b.jpg
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach Not found");
        });
    }

    public String deleted(String fileId) {

        AttachEntity attach = get(fileId);
        String path = "attaches/" + attach.getPath() + "/" + fileId + "." + attach.getExtension();

        Path file = Paths.get(path);
        try {
            Files.delete(file);
            attachRepository.delete(attach);
            return "success";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PageImpl pagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "uuid");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachEntity> all = attachRepository.findAll(pageable);

        List<AttachEntity> list = all.getContent();

        List<AttachDTO> dtoList = new LinkedList<>();

        list.forEach(attach -> {
            AttachDTO dto = new AttachDTO();
            dto.setId(attach.getUuid());
            dto.setUrl(getAttachOpenUrl(attach.getUuid()));
            dto.setOriginalName(attach.getOriginalName());
            dto.setPath(attach.getPath());
            dtoList.add(dto);
        });

        return new PageImpl(dtoList, pageable, all.getTotalElements());
    }

    public String getAttachOpenUrl(String uuid) {
        return serverUrl + "attach/open?fileId=" + uuid;
    }

    public AttachDTO getAttachWithOpenUrl(String uuid) {
        return new AttachDTO(uuid, serverUrl + "attach/open?fileId=" + uuid);
    }
//
//    public AttachDTO saveToProfile(MultipartFile file) {
//
//
//        //ProfileEntity profile = profileService.getProfile();
//
//        Optional<ProfileEntity> optional = profileRepository.findByEmail(SpringSecurityUtil.getCurrentUser().getUsername());
//
//        if (optional.isEmpty()) {
//            throw new NoPermissionException("No user");
//
//        }
//        ProfileEntity profile = optional.get();
//
//        try {
//
//            File folder = new File(attachFolder + getYmDString());
//
////            split[0], split[1], file.getSize(), folder.getPath(), article
//            AttachEntity attach = new AttachEntity();
//            attach.setExtension(getExtension(file.getOriginalFilename()));
//            attach.setOriginalName(file.getOriginalFilename()
//                    .replace(("." + getExtension(file.getOriginalFilename())), ""));
//            attach.setSize(file.getSize());
//            attach.setPath(getYmDString());
//            attachRepository.save(attach);
//
//            profile.setPhoto(attach);
//            profileRepository.save(profile);
//
//            String fileName = attach.getUuid() + "." + getExtension(file.getOriginalFilename());
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(attachFolder + getYmDString() + "/" + fileName);
//            Files.write(path, bytes);
//
//            AttachDTO dto = new AttachDTO();
//            dto.setUrl(serverUrl + "attach/open?fileId=" + attach.getUuid());
//            return dto;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
