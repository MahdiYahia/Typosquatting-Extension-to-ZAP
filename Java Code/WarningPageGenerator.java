public class WarningPageGenerator {

	/**
	 * 
	 * @param result
	 */
	public String generateWarningPage(DetectionResult result) {
		return buildHtmlTemplate(result.getRequestedDomain(), result.getSuggestedDomain(), result.getType());
	}

	/**
	 * 
	 * @param requested
	 * @param suggested
	 * @param type
	 */
	public String buildHtmlTemplate(String requested, String suggested, TyposquattingType type) {
		String typeExplanation = getTypeExplanation(type);
		return "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset='UTF-8'>\n" +
            "    <title>ZAP Typosquatting Warning</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            background: #f5f5f5;\n" +
            "            margin: 0;\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        .warning {\n" +
            "            max-width: 700px;\n" +
            "            margin: 50px auto;\n" +
            "            background: white;\n" +
            "            padding: 40px;\n" +
            "            border-left: 5px solid #ff6b6b;\n" +
            "            box-shadow: 0 2px 10px rgba(0,0,0,0.1);\n" +
            "            border-radius: 5px;\n" +
            "        }\n" +
            "        h1 {\n" +
            "            color: #ff6b6b;\n" +
            "            margin-top: 0;\n" +
            "        }\n" +
            "        .domain {\n" +
            "            font-weight: bold;\n" +
            "            color: #333;\n" +
            "            background: #fff3cd;\n" +
            "            padding: 5px 10px;\n" +
            "            border-radius: 3px;\n" +
            "            font-family: monospace;\n" +
            "            font-size: 16px;\n" +
            "        }\n" +
            "        .suggested {\n" +
            "            background: #d1f2eb;\n" +
            "        }\n" +
            "        .explanation {\n" +
            "            background: #f8f9fa;\n" +
            "            padding: 15px;\n" +
            "            border-radius: 5px;\n" +
            "            margin: 20px 0;\n" +
            "        }\n" +
            "        .buttons {\n" +
            "            margin-top: 30px;\n" +
            "        }\n" +
            "        button {\n" +
            "            padding: 12px 24px;\n" +
            "            margin: 5px;\n" +
            "            cursor: pointer;\n" +
            "            border: none;\n" +
            "            border-radius: 5px;\n" +
            "            font-size: 14px;\n" +
            "            font-weight: bold;\n" +
            "        }\n" +
            "        .btn-danger {\n" +
            "            background: #ff6b6b;\n" +
            "            color: white;\n" +
            "        }\n" +
            "        .btn-danger:hover {\n" +
            "            background: #ff5252;\n" +
            "        }\n" +
            "        .btn-success {\n" +
            "            background: #51cf66;\n" +
            "            color: white;\n" +
            "        }\n" +
            "        .btn-success:hover {\n" +
            "            background: #40c057;\n" +
            "        }\n" +
            "        .btn-secondary {\n" +
            "            background: #868e96;\n" +
            "            color: white;\n" +
            "        }\n" +
            "        .btn-secondary:hover {\n" +
            "            background: #6c757d;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class='warning'>\n" +
            "        <h1>⚠️ Potential Typosquatting Detected</h1>\n" +
            "        <p>You attempted to visit: <span class='domain'>" + requested + "</span></p>\n" +
            "        <p>Did you mean: <span class='domain suggested'>" + suggested + "</span>?</p>\n" +
            "        <div class='explanation'>\n" +
            "            <strong>Detection reason:</strong> " + typeExplanation + "\n" +
            "        </div>\n" +
            "        <p>Typosquatting is a technique where attackers register domains similar to legitimate sites to steal credentials or spread malware.</p>\n" +
            "        <div class='buttons'>\n" +
            "            <form method='POST' action='http://zap/typosquatting-handler'>\n" +
            "                <input type='hidden' name='requested' value='" + requested + "'>\n" +
            "                <input type='hidden' name='suggested' value='" + suggested + "'>\n" +
            "                <button type='submit' name='action' value='suggested' class='btn-success'>\n" +
            "                    ✓ Go to " + suggested + "\n" +
            "                </button>\n" +
            "                <button type='submit' name='action' value='proceed' class='btn-danger'>\n" +
            "                    Proceed to " + requested + " anyway\n" +
            "                </button>\n" +
            "                <button type='submit' name='action' value='cancel' class='btn-secondary'>\n" +
            "                    Cancel\n" +
            "                </button>\n" +
            "            </form>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
 	}
	private String getTypeExplanation(TyposquattingType type) {
			switch (type) {
				case MISSING_CHARACTER:
					return "One character is missing from the legitimate domain name.";
				case EXTRA_CHARACTER:
					return "An extra character was added to the legitimate domain name.";
				case REPLACED_CHARACTER:
					return "One character was replaced in the legitimate domain name.";
				case SWAPPED_CHARACTERS:
					return "Two adjacent characters were swapped in the legitimate domain name.";
				case COMBOSQUATTING:
					return "A word was appended to the legitimate domain name.";
				default:
					return "Unknown typosquatting pattern detected.";
			}
	}
}