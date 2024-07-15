package practice.sessionlogin;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final Map<String, Map<String, Object>> sessions = new ConcurrentHashMap<>();

    public Map<String, Object> getSession(String sessionId, boolean create) {
        Map<String, Object> session = sessions.get(sessionId);
        if (session == null && create) {
            session = new HashMap<>();
            sessions.put(sessionId, session);
        }
        return session;
    }

    public void invalidate(String sessionId) {
        Map<String, Object> session = sessions.get(sessionId);
        if (session != null) {
            sessions.remove(sessionId);
        }
    }
}
