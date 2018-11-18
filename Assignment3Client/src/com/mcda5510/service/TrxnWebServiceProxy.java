package com.mcda5510.service;

public class TrxnWebServiceProxy implements com.mcda5510.service.TrxnWebService {
  private String _endpoint = null;
  private com.mcda5510.service.TrxnWebService trxnWebService = null;
  
  public TrxnWebServiceProxy() {
    _initTrxnWebServiceProxy();
  }
  
  public TrxnWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initTrxnWebServiceProxy();
  }
  
  private void _initTrxnWebServiceProxy() {
    try {
      trxnWebService = (new com.mcda5510.service.TrxnWebServiceServiceLocator()).getTrxnWebService();
      if (trxnWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)trxnWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)trxnWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (trxnWebService != null)
      ((javax.xml.rpc.Stub)trxnWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.mcda5510.service.TrxnWebService getTrxnWebService() {
    if (trxnWebService == null)
      _initTrxnWebServiceProxy();
    return trxnWebService;
  }
  
  public void test() throws java.rmi.RemoteException{
    if (trxnWebService == null)
      _initTrxnWebServiceProxy();
    trxnWebService.test();
  }
  
  public void createTrxns(com.mcda5510.entity.Transaction trxn) throws java.rmi.RemoteException{
    if (trxnWebService == null)
      _initTrxnWebServiceProxy();
    trxnWebService.createTrxns(trxn);
  }
  
  public void updateTrxns(com.mcda5510.entity.Transaction trxn) throws java.rmi.RemoteException{
    if (trxnWebService == null)
      _initTrxnWebServiceProxy();
    trxnWebService.updateTrxns(trxn);
  }
  
  public void initConnection() throws java.rmi.RemoteException{
    if (trxnWebService == null)
      _initTrxnWebServiceProxy();
    trxnWebService.initConnection();
  }
  
  public void getSingleTrxn(java.lang.String id) throws java.rmi.RemoteException{
    if (trxnWebService == null)
      _initTrxnWebServiceProxy();
    trxnWebService.getSingleTrxn(id);
  }
  
  public void removeTrxns(java.lang.String id) throws java.rmi.RemoteException{
    if (trxnWebService == null)
      _initTrxnWebServiceProxy();
    trxnWebService.removeTrxns(id);
  }
  
  public com.mcda5510.entity.Transaction resetTrxn(com.mcda5510.entity.Transaction trxn) throws java.rmi.RemoteException{
    if (trxnWebService == null)
      _initTrxnWebServiceProxy();
    return trxnWebService.resetTrxn(trxn);
  }
  
  
}