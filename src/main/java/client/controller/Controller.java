package client.controller;

import java.awt.Component;

import client.util.Util;

public abstract class Controller {

	public void showError(final Component parentComponent, final String msg) {
		Util.showErrorMessage(parentComponent, msg);
	}

	public void showInfo(final Component parentComponent, final String msg) {
		Util.showInfoMessage(parentComponent, msg);
	}

}
