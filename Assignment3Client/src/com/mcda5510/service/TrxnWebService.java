/**
 * TrxnWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mcda5510.service;

public interface TrxnWebService extends java.rmi.Remote {
    public void test() throws java.rmi.RemoteException;
    public void createTrxns(com.mcda5510.entity.Transaction trxn) throws java.rmi.RemoteException;
    public void updateTrxns(com.mcda5510.entity.Transaction trxn) throws java.rmi.RemoteException;
    public void initConnection() throws java.rmi.RemoteException;
    public void getSingleTrxn(java.lang.String id) throws java.rmi.RemoteException;
    public void removeTrxns(java.lang.String id) throws java.rmi.RemoteException;
    public com.mcda5510.entity.Transaction resetTrxn(com.mcda5510.entity.Transaction trxn) throws java.rmi.RemoteException;
}
