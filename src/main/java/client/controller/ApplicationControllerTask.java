package client.controller;

import javax.swing.SwingWorker;

import client.view.ProgressDialog;

public class ApplicationControllerTask<T> extends SwingWorker<T, Void> {

	private TaskCore<T> taskCore;

	public ApplicationControllerTask(TaskCore<T> taskCore) {
		this.taskCore = taskCore;
	}

	@Override
	protected T doInBackground() throws Exception {
		setProgress(1);
		return taskCore.run();
		
	}

	@Override
	protected void done() {
		super.done();
		setProgress(100);
	}
}
