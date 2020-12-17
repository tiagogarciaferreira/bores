package com.tgfcodes.bores.event;

import com.tgfcodes.bores.model.PasswordReset;

public class PasswordResetEvent {

	private PasswordReset passwordReset;
	private String url;

	public PasswordResetEvent(PasswordReset passwordReset, String url) {
		this.passwordReset = passwordReset;
		this.url = url;
	}

	public PasswordReset getPasswordReset() {
		return passwordReset;
	}
	
	public String getUrl() {
		return url;
	}

}
