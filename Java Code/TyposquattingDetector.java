import java.util.Arrays;
import java.util.List;

public class TyposquattingDetector extends BaseDetector {

	private List<String> combosquattingList;
	/**
	 * 
	 * @param storage
	 */
	public TyposquattingDetector(DomainStorage storage) {
		super(storage);
        this.combosquattingList = Arrays.asList(
            "login", "secure", "account", "verify",
            "support", "help", "admin", "service",
            "auth", "signin", "my"
        );
	}

	/**
	 * 
	 * @param requestedDomain
	 * @param knownDomain
	 */
	@Override
	protected boolean hasMissingCharacter(String requestedDomain, String knownDomain) {
		if (knownDomain.length()-requestedDomain.length() != 1) {
            return false;
        }
		for(int i = 0 ; i<requestedDomain.length(); i++) {
			for(char c='a'; c<='z'; c++){
				String modifiedDomain = requestedDomain.substring(0, i) + c + requestedDomain.substring(i);
				if(modifiedDomain.equals(knownDomain)) {
					return true;
				}
			}
			for(char c: new char[]{'.','-','_'}) {
			String modifiedDomain = requestedDomain.substring(0, i) + c + requestedDomain.substring(i);
				if(modifiedDomain.equals(knownDomain)) {
					return true;
				}
			}
			for(char c='0'; c<='9'; c++){
				String modifiedDomain = requestedDomain.substring(0, i) + c + requestedDomain.substring(i);
				if(modifiedDomain.equals(knownDomain)) {
					return true;
				}
			}
		}		
		return false;
	}
	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	@Override
	protected boolean hasExtraCharacter(String requestedDomain, String knownDomain) {
		if (requestedDomain.length()-knownDomain.length() != 1) {
			return false;
		}
		for(int i = 0 ; i<knownDomain.length(); i++) {
			for(char c='a'; c<='z'; c++){
				String modifiedDomain = knownDomain.substring(0, i) + c + knownDomain.substring(i);
				if(modifiedDomain.equals(requestedDomain)) {
					return true;
				}
			}
			for(char c: new char[]{'.','-','_'}) {
			String modifiedDomain = knownDomain.substring(0, i) + c + knownDomain.substring(i);
				if(modifiedDomain.equals(requestedDomain)) {
					return true;
				}
			}
			for(char c='0'; c<='9'; c++){
				String modifiedDomain = knownDomain.substring(0, i) + c + knownDomain.substring(i);
				if(modifiedDomain.equals(requestedDomain)) {
					return true;
				}
			}
		}		
		return false;
	}

	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	@Override
	protected boolean hasReplacedCharacter(String domain1, String domain2) {
		if (domain1.length() != domain2.length()) {
            return false;
        }
        
        int differences = 0;
        for (int i = 0; i < domain1.length(); i++) {
            if (domain1.charAt(i) != domain2.charAt(i)) {
                differences++;
                if (differences > 1) {
                    return false; 
                }
            }
        }
        
        return differences == 1;
	}

	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	@Override
	protected boolean hasSwappedCharacters(String domain1, String domain2) {
		if (domain1.length() != domain2.length()) {
            return false;
        }
        
        for (int i = 0; i < domain1.length() - 1; i++) {
            char[] chars = domain1.toCharArray();
            char temp = chars[i];
            chars[i] = chars[i + 1];
            chars[i + 1] = temp;
            String swapped = new String(chars);
            
            if (swapped.equals(domain2)) {
                return true;
            }
        }
        
        return false;
	}

	/**
	 * 
	 * @param domain1
	 * @param domain2
	 */
	@Override
	protected boolean hasCombosquatting(String domain1, String domain2) {
		for (String combo : combosquattingList) {
			if (domain1.equals(combo + domain2) || domain1.equals(domain2 + combo)) {
				return true;
			}
		}
		return false;
	}

}