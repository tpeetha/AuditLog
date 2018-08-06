package us.deloitteinnovation.tieout.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditEventPublisher implements ApplicationEventPublisherAware  {

    @Value("${enableAudit}")
    private boolean enableAudit;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(
        ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(AuditEvent event) {
        if (this.publisher != null && enableAudit) {
            this.publisher.publishEvent(new AuditApplicationEvent(event));
        }
    }
}