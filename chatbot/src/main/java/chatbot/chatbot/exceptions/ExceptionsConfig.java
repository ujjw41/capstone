package chatbot.chatbot.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ExceptionsConfig extends UsernameNotFoundException {

	public ExceptionsConfig(String msg) {
		super(msg);
	}

	public ExceptionsConfig(String msg, Throwable cause) {
		super(msg, cause);
	}
}
