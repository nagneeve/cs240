package server;

import java.io.IOException;
import java.util.logging.*;

import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.model.Model;
import shared.model.ModelException;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DownloadBatch_Handler implements HttpHandler {

	private Logger logger = Logger.getLogger("DownloadBatch_Handler");

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		// Process AddContact request
		// 1. Deserialize the request object from the request body
		// 2. Extract the new contact object from the request object
		// 3. Call the model to add the new contact
		
		XStream xStream = new XStream(new DomDriver());
		Model model = new Model();
		DownloadBatch_Result result = null;

		DownloadBatch_Params params = (DownloadBatch_Params)xStream.fromXML(exchange.getRequestBody());
		try {
			result = model.DownloadBatch(params);
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exchange.sendResponseHeaders(200, 0);
		xStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
		
	}
}