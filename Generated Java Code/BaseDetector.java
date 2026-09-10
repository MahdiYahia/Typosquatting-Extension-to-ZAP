import java.util.Arrays;
import java.util.List;

public abstract class BaseDetector {

	protected List<String> commonnDomains;
    protected DomainStorage storage;

	public BaseDetector(DomainStorage storage) {
        this.storage = storage;
        this.commonDomains = Arrays.asList(
            "google.com", "facebook.com", "amazon.com", "paypal.com",
            "microsoft.com", "apple.com", "twitter.com", "instagram.com",
            "linkedin.com", "youtube.com", "netflix.com", "ebay.com",
            "wikipedia.org", "reddit.com", "github.com", "stackoverflow.com",
            "dropbox.com", "yahoo.com", "bing.com", "outlook.com"
        );
    }
	/**
	 * 
	 * @param requestedDomain
	 */
	public final DetectionResult checkDomain(String requestedDomain) {
		requestedDomain = requestedDomain.toLowerCase().trim();

		//Domains to check against
		List<String> domainsToCheck = storage.get();

	}

	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	protected abstract boolean hasMissingCharacter(String domain1, String domain2);

	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	protected abstract boolean hasExtraCharacter(String domain1, String domain2);

	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	protected abstract boolean hasReplacedCharacter(String domain1, String domain2);

	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	protected abstract boolean hasSwappedCharacters(String domain1, String domain2);

	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	protected abstract boolean hasCombosquatting(String domain1, String domain2);

}