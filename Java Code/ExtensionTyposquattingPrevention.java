import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ExtensionTyposquattingPrevention extends ExtensionAdaptor {

	private TyposquattingDetector detector;
	private WarningPageGenerator warningPage;
	private DomainStorage storage;
	private WarningPageHandler warningPageHandler;

	@Override
	public void hook() {
		String zapHome = System.getProperty("zap.home");
		String storageFilePath = zapHome + "/typosquatting_storage.json";
		this.storage = new DomainStorage(storageFilePath);
		this.storage.loadFromDisk();

		detector = new TyposquattingDetector(storage);
		warningPage = new WarningPageGenerator();
		warningPageHandler = new WarningPageHandler(storage);
	}
	@Override
	public void unload() {
		storage.saveToDisk();		
	}

	/**
	 * 
	 * @param msg
	 */
	@Override
	public boolean onHttpRequestSend(HttpMessage msg) {
		try{
			URI uri = msg.getRequestHeader().getURI();
			String domain = uri.getHost();
			String path = uri.getPath();
			if (domain == null || domain.isEmpty()){
				return true;
			}
			if (path!= null&& path.equals("/typosquatting-handler")){
				handleWarningPageRequest(msg);
				return false;
			}
			domain = domain.toLowerCase().trim();
			if (storage.isWhiteListed(domain)){
				return true;
			}
			DetectionResult result = detector.checkDomain(domain);
			if(result.isSuspicious()){
				String warningHtml = warningPage.generateWarningPage(result);
				msg.setResponseBody(warningHtml);
				return false;
			}
			storage.addVisitedDomain(domain);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return true;
		}
	}
	
	private void handleWarningPageRequest(HttpMessage msg) {
		try {
			String body = msg.getRequestBody().toString();
			Map<String, String> params = parseFromData(body);
			String action = params.get("action");
			String requestedDomain = params.get("requested");
			String suggestedDomain = params.get("suggested");
			warningPageHandler.handleUserChoice(msg, action, requestedDomain, suggestedDomain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> parseFormData(String body) {
        Map<String, String> params = new HashMap<>();
        
        if (body == null || body.isEmpty()) {
            return params;
        }
        
        try {
            String[] pairs = body.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], "UTF-8");
                    String value = URLDecoder.decode(keyValue[1], "UTF-8");
                    params.put(key, value);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing form data: " + e.getMessage());
        }
        
        return params;
    }
	
	public boolean canHookOnHttpRequestSend() {
		return true;
	}
	@Override 
	public String getAuthor() {
		return "Group 11";
	}
	@Override
	public String getDescription() {
		return "An extension to prevent typosquatting attacks by detecting suspicious domains and warning users.";
	}
	@Override
	public String getName() {
		return "Typosquatting Prevention Extension";
	}

}