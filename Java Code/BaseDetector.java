import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseDetector {

	protected List<String> knownDomains;
    protected DomainStorage storage;
	/**
	 * 
	 * @param requestedDomain
	 */
	public BaseDetector(DomainStorage storage) {
        this.storage = storage;
        this.knownDomains = Arrays.asList(
            "google.com", "facebook.com", "amazon.com", "paypal.com",
            "microsoft.com", "apple.com", "twitter.com", "instagram.com",
            "linkedin.com", "youtube.com", "netflix.com", "ebay.com",
            "wikipedia.org", "reddit.com", "github.com", "stackoverflow.com",
            "dropbox.com", "yahoo.com", "bing.com", "outlook.com"
        );
    }
	public DetectionResult checkDomain(String requestedDomain) {
		requestedDomain = requestedDomain.toLowerCase().trim();
		//Get the domains to check against
		Set<String> domainsToCheck = new HashSet<>();
		if (storage.isWhiteListed(requestedDomain)) {
		    return new DetectionResult(false, requestedDomain, null, TyposquattingType.NONE);
		}
		domainsToCheck.addAll(knownDomains);
		domainsToCheck.addAll(storage.getVisitedDomains());
		for (String knownDomain : domainsToCheck) {
		    if (requestedDomain.equals(knownDomain)) continue;
		   
		  	if (hasMissingCharacter(requestedDomain, knownDomain)) {
                return new DetectionResult(true, requestedDomain, knownDomain, 
                                          TyposquattingType.MISSING_CHARACTER);
            }
            if (hasExtraCharacter(requestedDomain, knownDomain)) {
                return new DetectionResult(true, requestedDomain, knownDomain,
                                          TyposquattingType.EXTRA_CHARACTER);
            }
            if (hasReplacedCharacter(requestedDomain, knownDomain)) {
                return new DetectionResult(true, requestedDomain, knownDomain,
                                          TyposquattingType.REPLACED_CHARACTER);
            }
            if (hasSwappedCharacters(requestedDomain, knownDomain)) {
                return new DetectionResult(true, requestedDomain, knownDomain,
                                          TyposquattingType.SWAPPED_CHARACTERS);
            }
			if (hasCombosquatting(requestedDomain, knownDomain)) {
                return new DetectionResult(true, requestedDomain, knownDomain,
                                          TyposquattingType.COMBOSQUATTING);
            } 
        }
		return new DetectionResult(false, requestedDomain, null, TyposquattingType.NONE);;
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
	protected abstract boolean hasReplacedCharacter(String domain1, String domain2);	/**
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