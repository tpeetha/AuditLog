package us.deloitteinnovation.tieout.audit;

import org.springframework.boot.actuate.audit.AuditEvent;
import us.deloitteinnovation.tieout.user.utils.TieoutUtil;

import java.util.Date;
import java.util.Map;

/**
 * Created by mnallathiga on 30-11-2016.
 */
public class AuditUtil {

    /**
     * Private constructor
     */
    private AuditUtil(){
        // This is to hide the visibility of the class
    }

    /**
     * Description: This method is used to create an audit event.
     *
     * @param eventType
     *     - type of event
     * @param data
     *     - data needs to audit
     *
     * @return - AuditEvent
     */
    public static AuditEvent createAuditEvent(EventType eventType, Map<String, Object> data) {
        return new AuditEvent(new Date(), TieoutUtil.getUserName(), eventType.name(), data);
    }

    public static AuditEvent createAuditEvent(EventType eventType, Map<String, Object> auditContext, Map<String, Object> data) {
        auditContext.putAll(data);
        return createAuditEvent(eventType, auditContext);
    }
}
