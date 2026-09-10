public class WarningPageHandler {

	private DomainStorage storage;

	/**
	 * 
	 * @param storage
	 */
	public WarningPageHandler(DomainStorage storage) {
		this.storage = storage;
	}

	
	public void handleUserChoice(HttpMessage msg, String choice, String requestedDomain, String suggestedDomain) {
		if (choice == null || requestedDomain == null) {
			return;
		}
		try {
			switch (choice.toLowerCase()) {
				case "proceed":				
					storage.addWhiteListedDomain(requestedDomain);
					String proceedRedirect = 
							"HTTP/1.1 302 Found\r\n" +
							"Location: http://" + requestedDomain + "\r\n" +
							"Content-Type: text/html\r\n" +
							"\r\n" +
							"<html><body>Redirecting to " + requestedDomain + "...</body></html>";
						msg.setResponseHeader(proceedRedirect);
						break;
                    
                case "suggested":
                    if (suggestedDomain != null) {
                        storage.addVisitedDomain(suggestedDomain);
                        String suggestedRedirect = 
                            "HTTP/1.1 302 Found\r\n" +
                            "Location: http://" + suggestedDomain + "\r\n" +
                            "Content-Type: text/html\r\n" +
                            "\r\n" +
                            "<html><body>Redirecting to " + suggestedDomain + "...</body></html>";
                        msg.setResponseHeader(suggestedRedirect);
                    }
                    break;
                    
                case "cancel":
                    String cancelHtml = 
                        "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head><title>Request Cancelled</title></head>\n" +
                        "<body>\n" +
                        "  <h2>Request Cancelled</h2>\n" +
                        "  <p>The request has been cancelled for your protection.</p>\n" +
                        "</body>\n" +
                        "</html>";
                    msg.setResponseHeader(
                        "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + cancelHtml.length() + "\r\n" +
                        "\r\n"
                    );
                    msg.setResponseBody(cancelHtml);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error handling user choice: " + e.getMessage());
        }						

	}

}