package com.redstream.cameraprovider;

public class CameraProviderImpl implements CameraProvider {

	@Override
	public String takePhoto() {
		//random number generator to return different outputs
		int num = ((int) (Math.floor(Math.random() * 100)) % 4);
		String photo;
		
		switch (num){
			case 0: {
				photo = "¯\\_(UwU)_/¯";
				break;
			}
			case 1: {
				photo = "\\(^w^)/";
				break;
			}
			case 2: {
				photo = "\\(@o@)/";
				break;
			}
			default: {
				photo = "\\(XoX)/";
				break;
			}
		}
		
		return photo;
	}

}
