function XeditService(url){
    this.base = Xedit2ServiceBase;
    this.base(url);
}

XeditService.prototype = new Xedit2ServiceBase;

XeditService.prototype.getUser = function(id){
    var param=new SOAPClientParameters;
    param.add('id',id);
    var result = SOAPClient.invode(this._url,"getUser",param,false);
    return result;
}

function Xedit2ServiceBase(url){
    this._url=url;
}

Xedit2ServiceBase.prototype.toString = function(){
    return this._url;
}