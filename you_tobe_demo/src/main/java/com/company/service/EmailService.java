package com.company.service;

import com.company.dto.EmailHistoryDTO;
import com.company.entity.EmailHistoryEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.EmailRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromAccount;

    public void sendRegistrationEmail(String toAccount, ProfileEntity entity) {
//        String message = "Your Activation lin: adsdasdasdasda";
//        sendSimpleEmail(toAccount, "Registration", message);
        StringBuilder builder = new StringBuilder();
        builder.append("<h1 style='align-text:center'>Salom Jiga Qalaysan</h1>");
        builder.append("<b>Mazgi</b>");
        builder.append("<p> <a href='http://localhost:8080/auth/email/verification/" +
                JwtUtil.encode(entity.getId()) + "'>Link verification</a> </p>");
        sendEmail(toAccount, "Registration", builder.toString());

        EmailHistoryEntity entity1 = new EmailHistoryEntity();
        entity1.setEmail(toAccount);

        emailRepository.save(entity1);
    }

    private void sendEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendSimpleEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        msg.setFrom(fromAccount);
        javaMailSender.send(msg);
    }

    public Long countVerivifationSending(String email) {

        return emailRepository.countResend(email);
    }

    public boolean verificationTime(String email) {

        Optional<EmailHistoryEntity> optional = emailRepository
                .findTopByEmailOrderByCreatedDateDesc(email);

        if (optional.isEmpty()) {
            return false;
        }

        EmailHistoryEntity history = optional.get();

        return history.getCreatedDate().plusMinutes(1).isAfter(LocalDateTime.now());
    }

    public PageImpl pagination(int page, int size) {
        // page = 1
    /*    Iterable<TypesEntity> all = typesRepository.pagination(size, size * (page - 1));
        long totalAmount = typesRepository.countAllBy();*/
//        long totalAmount = all.getTotalElements();
//        int totalPages = all.getTotalPages();

//        TypesPaginationDTO paginationDTO = new TypesPaginationDTO(totalAmount, dtoList);
//        return paginationDTO;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EmailHistoryEntity> all = emailRepository.findAll(pageable);

        List<EmailHistoryEntity> list = all.getContent();

        List<EmailHistoryDTO> dtoList = new LinkedList<>();

        list.forEach(email -> {
            EmailHistoryDTO dto = new EmailHistoryDTO();
            dto.setId(email.getId());
            dto.setEmail(email.getEmail());
            dto.setCreateddDate(email.getCreatedDate());

            dtoList.add(dto);
        });

        return new PageImpl(dtoList,pageable, all.getTotalElements());
    }
}
