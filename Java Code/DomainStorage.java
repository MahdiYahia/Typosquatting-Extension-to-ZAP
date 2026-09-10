import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.json.*;

public class DomainStorage {

	private Set<String> visitedDomains;
	private Set<String> whiteListedDomains;
	private String storageFilePath;

	/**
	 * 
	 * @param filePath
	 */
	public DomainStorage(String filePath) {
		this.storageFilePath = filePath;
        this.visitedDomains = new HashSet<>();
        this.whiteListedDomains = new HashSet<>();
	}

	/**
	 * 
	 * @param domain
	 */
	public void addVisitedDomain(String domain) {
		if (domain != null && !domain.trim().isEmpty()) {
            visitedDomains.add(domain.toLowerCase().trim());
            saveToDisk();
		}
	}

	/**
	 * 
	 * @param domain
	 */
	public void addWhiteListedDomain(String domain) {
		if (domain != null && !domain.trim().isEmpty()) {
            whiteListedDomains.add(domain.toLowerCase().trim());
            saveToDisk();
        }
	}

	/**
	 * 
	 * @param domain
	 */
	public boolean isWhiteListed(String domain) {
		return whiteListedDomains.contains(domain.toLowerCase().trim());
	}

	public Set<String> getVisitedDomains() {
		return this.visitedDomains;
	}

	public void loadFromDisk() {
		File file = new File(storageFilePath);
		
		if (!file.exists()) {
            return;
        }
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(storageFilePath)));
            JSONObject json = new JSONObject(content);
            
            if (json.has("visitedDomains")) {
                JSONArray visitedArray = json.getJSONArray("visitedDomains");
                for (int i = 0; i < visitedArray.length(); i++) {
                    visitedDomains.add(visitedArray.getString(i));
                }
            }
            
            if (json.has("whitelistedDomains")) {
                JSONArray whitelistArray = json.getJSONArray("whitelistedDomains");
                for (int i = 0; i < whitelistArray.length(); i++) {
                    whiteListedDomains.add(whitelistArray.getString(i));
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error loading from storage: " + e.getMessage());
        }		
	}

	public void saveToDisk() {
		try {
            File file = new File(storageFilePath);
            file.getParentFile().mkdirs();
            
            JSONObject json = new JSONObject();
            json.put("visitedDomains", new JSONArray(visitedDomains));
            json.put("whitelistedDomains", new JSONArray(whiteListedDomains));
            json.put("lastUpdated", new Date().toString());
            
            Files.write(Paths.get(storageFilePath), json.toString(2).getBytes());
            
        } catch (Exception e) {
            System.err.println("Error saving to storage: " + e.getMessage());
        }
	}

}