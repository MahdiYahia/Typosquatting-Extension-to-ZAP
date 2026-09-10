public class ExtensionTyposquattingPrevention extends ExtensionAdaptor {

	private TyposquattingDetector detector;
	private WarningPageGenerator warningPage;
	private DomainStorage storage;
	private WarningPageHandler warningPageHandler;

	@Override
	public void hook() {
		// TODO - implement ExtensionTyposquattingPrevention.hook
		throw new UnsupportedOperationException();
	}

	public void unload() {
		// TODO - implement ExtensionTyposquattingPrevention.unload
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param msg
	 */
	public boolean onHttpRequestSend(HttpMessage msg) {
		// TODO - implement ExtensionTyposquattingPrevention.onHttpRequestSend
		throw new UnsupportedOperationException();
	}

	public boolean canHookOnHttpRequestSend() {
		// TODO - implement ExtensionTyposquattingPrevention.canHookOnHttpRequestSend
		throw new UnsupportedOperationException();
	}

}