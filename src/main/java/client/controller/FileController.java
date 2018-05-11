package client.controller;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import client.service.ServiceLocator;
import common.model.File;
import common.service.ServiceException;

public class FileController extends Controller {

	public List<File> searchFilesbyNamePart(String fileNamePart){
		try {
			return ServiceLocator.getService().getFileByNamePart(fileNamePart);
		} catch (RemoteException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	public File searchSpecificFile(String fileName) {
		try {
			return ServiceLocator.getService().getFileByName(fileName);
		} catch (RemoteException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
