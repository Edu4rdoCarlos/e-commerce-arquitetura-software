package app;

import event.EmailEventListener;
import service.EmailService;

import java.util.logging.Logger;

public class EmailServiceApplication {
    private static final Logger logger = Logger.getLogger(EmailServiceApplication.class.getName());

    public static void main(String[] args) {
        logger.info("Iniciando Email Service...");
        
        EmailService emailService = new EmailService();
        EmailEventListener emailEventListener = new EmailEventListener(emailService);
        
        emailEventListener.start();
        
        logger.info("Email Service iniciado com sucesso. Aguardando eventos...");
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Desligando Email Service...");
            emailEventListener.stop();
            logger.info("Email Service desligado com sucesso.");
        }));
    }
}
