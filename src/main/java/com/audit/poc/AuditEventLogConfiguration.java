package us.deloitteinnovation.tieout.audit;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import us.deloitteinnovation.tieout.user.utils.TieoutConstants;

import java.util.LinkedHashMap;
import java.util.Map;

import static us.deloitteinnovation.tieout.user.utils.TieoutConstants.CLIENTID_STR;
import static us.deloitteinnovation.tieout.user.utils.TieoutConstants.ELEMENTID_STR;
import static us.deloitteinnovation.tieout.user.utils.TieoutConstants.REVISION_ID_STR;
import static us.deloitteinnovation.tieout.user.utils.TieoutConstants.SECTION_ID_STR;
import static us.deloitteinnovation.tieout.user.utils.TieoutConstants.STATEMENTID_STR;

@Configuration
public class AuditEventLogConfiguration implements ApplicationListener<AuditApplicationEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(AuditEventLogConfiguration.class);
    private static final String TAB = "\t";
    private Gson gson = new Gson();

    @Override
    public void onApplicationEvent(AuditApplicationEvent event) {
        LOG.info(buildLogMessage(event));
    }

    private String buildLogMessage(AuditApplicationEvent event) {
        AuditEvent source = (AuditEvent) event.getSource();
        StringBuilder builder = new StringBuilder();
        Map<String, Object> data = source.getData();
        final int size = 18;
        builder.append(" [").append(source.getPrincipal()).append("]").append(TAB);

        String method = String.valueOf(data.getOrDefault("method","-"));
        method = StringUtils.center(String.format("[%s]",method), 7);
        String uri = String.valueOf(data.getOrDefault("uri","-"));
        builder.append(method).append(TAB);

        if(data.containsKey(CLIENTID_STR)) {
            final String msg = StringUtils.center(String.format("[Client-ID:%s]", data.get(CLIENTID_STR)), size);
            builder.append(msg).append(TAB);
        }
        if(data.containsKey(STATEMENTID_STR)) {
            final String msg = StringUtils.center(String.format("[Statement-ID:%s]", data.get(STATEMENTID_STR)), size);
            builder.append(msg).append(TAB);
        }
        if(data.containsKey(REVISION_ID_STR)) {
            final String msg = StringUtils.center(String.format("[Revision-ID:%s]", data.get(REVISION_ID_STR)), size);
            builder.append(msg).append(TAB);
        }
        if(data.containsKey(ELEMENTID_STR)) {
            final String msg = StringUtils.center(String.format("[Element-ID:%s]", data.get(ELEMENTID_STR)), size);
            builder.append(msg).append(TAB);
        }
        if(data.containsKey(SECTION_ID_STR)) {
            final String msg = StringUtils.center(String.format("[Section-ID:%s]", data.get(SECTION_ID_STR)), size);
            builder.append(msg).append(TAB);
        }

        builder.append(source.getType()).append(TAB).
            append(uri).append(TAB).append(" [Data:").
            append(gson.toJson(data)).append("]");
        return builder.toString();
    }

}
