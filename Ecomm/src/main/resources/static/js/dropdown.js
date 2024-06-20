function getSubCategories(catagoryId){
    var appname=window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/hgdiuj'));
	 document.getElementById('objDigProduct').action = appname+"/Dgproducts/savedigitalproducts?getQry=getSubsCatagory&catagoryId="+catagoryId;
	  document.getElementById('objDigProduct').submit();
}
