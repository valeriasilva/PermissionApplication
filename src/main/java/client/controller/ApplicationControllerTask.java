package client.controller;

import javax.swing.SwingWorker;

public class ApplicationControllerTask<T> extends SwingWorker<T, Void> {

	private TaskCore<T> taskCore;

	public ApplicationControllerTask(TaskCore<T> taskCore) {
		this.taskCore = taskCore;
	}

	@Override
	protected T doInBackground() throws Exception {
		return taskCore.run();
	}

	@Override
	protected void done() {
		super.done();
		taskCore.done();
	}
}
