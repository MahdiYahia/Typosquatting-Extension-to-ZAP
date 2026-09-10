public class DetectionResult {

	private boolean isSuspicious;
	private String requestedDomain;
	private String suggestedDomain;
	private TyposquattingType type;

	/**
	 * 
	 * @param suspicious
	 * @param requested
	 * @param suggested
	 * @param type
	 */
	public DetectionResult(boolean suspicious, String requested, String suggested, TyposquattingType type) {
		this.isSuspicious = suspicious;
        this.requestedDomain = requested;
        this.suggestedDomain = suggested;
        this.type = type;
	}

	public boolean isSuspicious() {
		return this.isSuspicious;
	}

	public String getRequestedDomain() {
		return this.requestedDomain;
	}

	public String getSuggestedDomain() {
		return this.suggestedDomain;
	}

	public TyposquattingType getType() {
		return this.type;
	}

}