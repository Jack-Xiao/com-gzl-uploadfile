var exec=require('cordova/exec');
var UploadFile={
	upload:function(action,successCallback,errorCallback){
		exec(successCallback,errorCallback,"UploadFile",action,[])
		//exec(successCallback, errorCallback, "Device", "getDeviceInfo", []);

};
}
module.exports=UploadFile;
