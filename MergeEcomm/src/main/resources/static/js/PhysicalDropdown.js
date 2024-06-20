function getSubCategory(catagoryId){
    var appname=window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/'));
    alert(appname);
	 document.getElementById('productobject').action = appname+"/productsphotos?getQry=getSubsCatagory&catagoryId="+catagoryId;
	  document.getElementById('productobject').submit();
}
