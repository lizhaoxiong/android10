/*___Generated_by_IDEA___*/

/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package android.net;
/** {@hide} */
public interface IIpMemoryStore extends android.os.IInterface
{
  /** Default implementation for IIpMemoryStore. */
  public static class Default implements android.net.IIpMemoryStore
  {
    /**
         * Store network attributes for a given L2 key.
         * If L2Key is null, choose automatically from the attributes ; passing null is equivalent to
         * calling findL2Key with the attributes and storing in the returned value.
         *
         * @param l2Key The L2 key for the L2 network. Clients that don't know or care about the L2
         *              key and only care about grouping can pass a unique ID here like the ones
         *              generated by {@code java.util.UUID.randomUUID()}, but keep in mind the low
         *              relevance of such a network will lead to it being evicted soon if it's not
         *              refreshed. Use findL2Key to try and find a similar L2Key to these attributes.
         * @param attributes The attributes for this network.
         * @param listener A listener that will be invoked to inform of the completion of this call,
         *                 or null if the client is not interested in learning about success/failure.
         * @return (through the listener) The L2 key. This is useful if the L2 key was not specified.
         *         If the call failed, the L2 key will be null.
         */
    @Override public void storeNetworkAttributes(java.lang.String l2Key, android.net.ipmemorystore.NetworkAttributesParcelable attributes, android.net.ipmemorystore.IOnStatusListener listener) throws android.os.RemoteException
    {
    }
    /**
         * Store a binary blob associated with an L2 key and a name.
         *
         * @param l2Key The L2 key for this network.
         * @param clientId The ID of the client.
         * @param name The name of this data.
         * @param data The data to store.
         * @param listener A listener to inform of the completion of this call, or null if the client
         *        is not interested in learning about success/failure.
         * @return (through the listener) A status to indicate success or failure.
         */
    @Override public void storeBlob(java.lang.String l2Key, java.lang.String clientId, java.lang.String name, android.net.ipmemorystore.Blob data, android.net.ipmemorystore.IOnStatusListener listener) throws android.os.RemoteException
    {
    }
    /**
         * Returns the best L2 key associated with the attributes.
         *
         * This will find a record that would be in the same group as the passed attributes. This is
         * useful to choose the key for storing a sample or private data when the L2 key is not known.
         * If multiple records are group-close to these attributes, the closest match is returned.
         * If multiple records have the same closeness, the one with the smaller (unicode codepoint
         * order) L2 key is returned.
         * If no record matches these attributes, null is returned.
         *
         * @param attributes The attributes of the network to find.
         * @param listener The listener that will be invoked to return the answer.
         * @return (through the listener) The L2 key if one matched, or null.
         */
    @Override public void findL2Key(android.net.ipmemorystore.NetworkAttributesParcelable attributes, android.net.ipmemorystore.IOnL2KeyResponseListener listener) throws android.os.RemoteException
    {
    }
    /**
         * Returns whether, to the best of the store's ability to tell, the two specified L2 keys point
         * to the same L3 network. Group-closeness is used to determine this.
         *
         * @param l2Key1 The key for the first network.
         * @param l2Key2 The key for the second network.
         * @param listener The listener that will be invoked to return the answer.
         * @return (through the listener) A SameL3NetworkResponse containing the answer and confidence.
         */
    @Override public void isSameNetwork(java.lang.String l2Key1, java.lang.String l2Key2, android.net.ipmemorystore.IOnSameL3NetworkResponseListener listener) throws android.os.RemoteException
    {
    }
    /**
         * Retrieve the network attributes for a key.
         * If no record is present for this key, this will return null attributes.
         *
         * @param l2Key The key of the network to query.
         * @param listener The listener that will be invoked to return the answer.
         * @return (through the listener) The network attributes and the L2 key associated with
         *         the query.
         */
    @Override public void retrieveNetworkAttributes(java.lang.String l2Key, android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener listener) throws android.os.RemoteException
    {
    }
    /**
         * Retrieve previously stored private data.
         * If no data was stored for this L2 key and name this will return null.
         *
         * @param l2Key The L2 key.
         * @param clientId The id of the client that stored this data.
         * @param name The name of the data.
         * @param listener The listener that will be invoked to return the answer.
         * @return (through the listener) The private data (or null), with the L2 key
         *         and the name of the data associated with the query.
         */
    @Override public void retrieveBlob(java.lang.String l2Key, java.lang.String clientId, java.lang.String name, android.net.ipmemorystore.IOnBlobRetrievedListener listener) throws android.os.RemoteException
    {
    }
    /**
         * Delete all data because a factory reset operation is in progress.
         */
    @Override public void factoryReset() throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements android.net.IIpMemoryStore
  {
    private static final java.lang.String DESCRIPTOR = "android.net.IIpMemoryStore";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an android.net.IIpMemoryStore interface,
     * generating a proxy if needed.
     */
    public static android.net.IIpMemoryStore asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof android.net.IIpMemoryStore))) {
        return ((android.net.IIpMemoryStore)iin);
      }
      return new android.net.IIpMemoryStore.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_storeNetworkAttributes:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          android.net.ipmemorystore.NetworkAttributesParcelable _arg1;
          if ((0!=data.readInt())) {
            _arg1 = android.net.ipmemorystore.NetworkAttributesParcelable.CREATOR.createFromParcel(data);
          }
          else {
            _arg1 = null;
          }
          android.net.ipmemorystore.IOnStatusListener _arg2;
          _arg2 = android.net.ipmemorystore.IOnStatusListener.Stub.asInterface(data.readStrongBinder());
          this.storeNetworkAttributes(_arg0, _arg1, _arg2);
          return true;
        }
        case TRANSACTION_storeBlob:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          java.lang.String _arg2;
          _arg2 = data.readString();
          android.net.ipmemorystore.Blob _arg3;
          if ((0!=data.readInt())) {
            _arg3 = android.net.ipmemorystore.Blob.CREATOR.createFromParcel(data);
          }
          else {
            _arg3 = null;
          }
          android.net.ipmemorystore.IOnStatusListener _arg4;
          _arg4 = android.net.ipmemorystore.IOnStatusListener.Stub.asInterface(data.readStrongBinder());
          this.storeBlob(_arg0, _arg1, _arg2, _arg3, _arg4);
          return true;
        }
        case TRANSACTION_findL2Key:
        {
          data.enforceInterface(descriptor);
          android.net.ipmemorystore.NetworkAttributesParcelable _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.net.ipmemorystore.NetworkAttributesParcelable.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          android.net.ipmemorystore.IOnL2KeyResponseListener _arg1;
          _arg1 = android.net.ipmemorystore.IOnL2KeyResponseListener.Stub.asInterface(data.readStrongBinder());
          this.findL2Key(_arg0, _arg1);
          return true;
        }
        case TRANSACTION_isSameNetwork:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          android.net.ipmemorystore.IOnSameL3NetworkResponseListener _arg2;
          _arg2 = android.net.ipmemorystore.IOnSameL3NetworkResponseListener.Stub.asInterface(data.readStrongBinder());
          this.isSameNetwork(_arg0, _arg1, _arg2);
          return true;
        }
        case TRANSACTION_retrieveNetworkAttributes:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener _arg1;
          _arg1 = android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener.Stub.asInterface(data.readStrongBinder());
          this.retrieveNetworkAttributes(_arg0, _arg1);
          return true;
        }
        case TRANSACTION_retrieveBlob:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          java.lang.String _arg2;
          _arg2 = data.readString();
          android.net.ipmemorystore.IOnBlobRetrievedListener _arg3;
          _arg3 = android.net.ipmemorystore.IOnBlobRetrievedListener.Stub.asInterface(data.readStrongBinder());
          this.retrieveBlob(_arg0, _arg1, _arg2, _arg3);
          return true;
        }
        case TRANSACTION_factoryReset:
        {
          data.enforceInterface(descriptor);
          this.factoryReset();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements android.net.IIpMemoryStore
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
           * Store network attributes for a given L2 key.
           * If L2Key is null, choose automatically from the attributes ; passing null is equivalent to
           * calling findL2Key with the attributes and storing in the returned value.
           *
           * @param l2Key The L2 key for the L2 network. Clients that don't know or care about the L2
           *              key and only care about grouping can pass a unique ID here like the ones
           *              generated by {@code java.util.UUID.randomUUID()}, but keep in mind the low
           *              relevance of such a network will lead to it being evicted soon if it's not
           *              refreshed. Use findL2Key to try and find a similar L2Key to these attributes.
           * @param attributes The attributes for this network.
           * @param listener A listener that will be invoked to inform of the completion of this call,
           *                 or null if the client is not interested in learning about success/failure.
           * @return (through the listener) The L2 key. This is useful if the L2 key was not specified.
           *         If the call failed, the L2 key will be null.
           */
      @Override public void storeNetworkAttributes(java.lang.String l2Key, android.net.ipmemorystore.NetworkAttributesParcelable attributes, android.net.ipmemorystore.IOnStatusListener listener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(l2Key);
          if ((attributes!=null)) {
            _data.writeInt(1);
            attributes.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_storeNetworkAttributes, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().storeNetworkAttributes(l2Key, attributes, listener);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      /**
           * Store a binary blob associated with an L2 key and a name.
           *
           * @param l2Key The L2 key for this network.
           * @param clientId The ID of the client.
           * @param name The name of this data.
           * @param data The data to store.
           * @param listener A listener to inform of the completion of this call, or null if the client
           *        is not interested in learning about success/failure.
           * @return (through the listener) A status to indicate success or failure.
           */
      @Override public void storeBlob(java.lang.String l2Key, java.lang.String clientId, java.lang.String name, android.net.ipmemorystore.Blob data, android.net.ipmemorystore.IOnStatusListener listener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(l2Key);
          _data.writeString(clientId);
          _data.writeString(name);
          if ((data!=null)) {
            _data.writeInt(1);
            data.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_storeBlob, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().storeBlob(l2Key, clientId, name, data, listener);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      /**
           * Returns the best L2 key associated with the attributes.
           *
           * This will find a record that would be in the same group as the passed attributes. This is
           * useful to choose the key for storing a sample or private data when the L2 key is not known.
           * If multiple records are group-close to these attributes, the closest match is returned.
           * If multiple records have the same closeness, the one with the smaller (unicode codepoint
           * order) L2 key is returned.
           * If no record matches these attributes, null is returned.
           *
           * @param attributes The attributes of the network to find.
           * @param listener The listener that will be invoked to return the answer.
           * @return (through the listener) The L2 key if one matched, or null.
           */
      @Override public void findL2Key(android.net.ipmemorystore.NetworkAttributesParcelable attributes, android.net.ipmemorystore.IOnL2KeyResponseListener listener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((attributes!=null)) {
            _data.writeInt(1);
            attributes.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_findL2Key, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().findL2Key(attributes, listener);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      /**
           * Returns whether, to the best of the store's ability to tell, the two specified L2 keys point
           * to the same L3 network. Group-closeness is used to determine this.
           *
           * @param l2Key1 The key for the first network.
           * @param l2Key2 The key for the second network.
           * @param listener The listener that will be invoked to return the answer.
           * @return (through the listener) A SameL3NetworkResponse containing the answer and confidence.
           */
      @Override public void isSameNetwork(java.lang.String l2Key1, java.lang.String l2Key2, android.net.ipmemorystore.IOnSameL3NetworkResponseListener listener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(l2Key1);
          _data.writeString(l2Key2);
          _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_isSameNetwork, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().isSameNetwork(l2Key1, l2Key2, listener);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      /**
           * Retrieve the network attributes for a key.
           * If no record is present for this key, this will return null attributes.
           *
           * @param l2Key The key of the network to query.
           * @param listener The listener that will be invoked to return the answer.
           * @return (through the listener) The network attributes and the L2 key associated with
           *         the query.
           */
      @Override public void retrieveNetworkAttributes(java.lang.String l2Key, android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener listener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(l2Key);
          _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_retrieveNetworkAttributes, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().retrieveNetworkAttributes(l2Key, listener);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      /**
           * Retrieve previously stored private data.
           * If no data was stored for this L2 key and name this will return null.
           *
           * @param l2Key The L2 key.
           * @param clientId The id of the client that stored this data.
           * @param name The name of the data.
           * @param listener The listener that will be invoked to return the answer.
           * @return (through the listener) The private data (or null), with the L2 key
           *         and the name of the data associated with the query.
           */
      @Override public void retrieveBlob(java.lang.String l2Key, java.lang.String clientId, java.lang.String name, android.net.ipmemorystore.IOnBlobRetrievedListener listener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(l2Key);
          _data.writeString(clientId);
          _data.writeString(name);
          _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_retrieveBlob, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().retrieveBlob(l2Key, clientId, name, listener);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      /**
           * Delete all data because a factory reset operation is in progress.
           */
      @Override public void factoryReset() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_factoryReset, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().factoryReset();
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      public static android.net.IIpMemoryStore sDefaultImpl;
    }
    static final int TRANSACTION_storeNetworkAttributes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_storeBlob = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_findL2Key = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_isSameNetwork = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_retrieveNetworkAttributes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_retrieveBlob = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_factoryReset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    public static boolean setDefaultImpl(android.net.IIpMemoryStore impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static android.net.IIpMemoryStore getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
       * Store network attributes for a given L2 key.
       * If L2Key is null, choose automatically from the attributes ; passing null is equivalent to
       * calling findL2Key with the attributes and storing in the returned value.
       *
       * @param l2Key The L2 key for the L2 network. Clients that don't know or care about the L2
       *              key and only care about grouping can pass a unique ID here like the ones
       *              generated by {@code java.util.UUID.randomUUID()}, but keep in mind the low
       *              relevance of such a network will lead to it being evicted soon if it's not
       *              refreshed. Use findL2Key to try and find a similar L2Key to these attributes.
       * @param attributes The attributes for this network.
       * @param listener A listener that will be invoked to inform of the completion of this call,
       *                 or null if the client is not interested in learning about success/failure.
       * @return (through the listener) The L2 key. This is useful if the L2 key was not specified.
       *         If the call failed, the L2 key will be null.
       */
  public void storeNetworkAttributes(java.lang.String l2Key, android.net.ipmemorystore.NetworkAttributesParcelable attributes, android.net.ipmemorystore.IOnStatusListener listener) throws android.os.RemoteException;
  /**
       * Store a binary blob associated with an L2 key and a name.
       *
       * @param l2Key The L2 key for this network.
       * @param clientId The ID of the client.
       * @param name The name of this data.
       * @param data The data to store.
       * @param listener A listener to inform of the completion of this call, or null if the client
       *        is not interested in learning about success/failure.
       * @return (through the listener) A status to indicate success or failure.
       */
  public void storeBlob(java.lang.String l2Key, java.lang.String clientId, java.lang.String name, android.net.ipmemorystore.Blob data, android.net.ipmemorystore.IOnStatusListener listener) throws android.os.RemoteException;
  /**
       * Returns the best L2 key associated with the attributes.
       *
       * This will find a record that would be in the same group as the passed attributes. This is
       * useful to choose the key for storing a sample or private data when the L2 key is not known.
       * If multiple records are group-close to these attributes, the closest match is returned.
       * If multiple records have the same closeness, the one with the smaller (unicode codepoint
       * order) L2 key is returned.
       * If no record matches these attributes, null is returned.
       *
       * @param attributes The attributes of the network to find.
       * @param listener The listener that will be invoked to return the answer.
       * @return (through the listener) The L2 key if one matched, or null.
       */
  public void findL2Key(android.net.ipmemorystore.NetworkAttributesParcelable attributes, android.net.ipmemorystore.IOnL2KeyResponseListener listener) throws android.os.RemoteException;
  /**
       * Returns whether, to the best of the store's ability to tell, the two specified L2 keys point
       * to the same L3 network. Group-closeness is used to determine this.
       *
       * @param l2Key1 The key for the first network.
       * @param l2Key2 The key for the second network.
       * @param listener The listener that will be invoked to return the answer.
       * @return (through the listener) A SameL3NetworkResponse containing the answer and confidence.
       */
  public void isSameNetwork(java.lang.String l2Key1, java.lang.String l2Key2, android.net.ipmemorystore.IOnSameL3NetworkResponseListener listener) throws android.os.RemoteException;
  /**
       * Retrieve the network attributes for a key.
       * If no record is present for this key, this will return null attributes.
       *
       * @param l2Key The key of the network to query.
       * @param listener The listener that will be invoked to return the answer.
       * @return (through the listener) The network attributes and the L2 key associated with
       *         the query.
       */
  public void retrieveNetworkAttributes(java.lang.String l2Key, android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener listener) throws android.os.RemoteException;
  /**
       * Retrieve previously stored private data.
       * If no data was stored for this L2 key and name this will return null.
       *
       * @param l2Key The L2 key.
       * @param clientId The id of the client that stored this data.
       * @param name The name of the data.
       * @param listener The listener that will be invoked to return the answer.
       * @return (through the listener) The private data (or null), with the L2 key
       *         and the name of the data associated with the query.
       */
  public void retrieveBlob(java.lang.String l2Key, java.lang.String clientId, java.lang.String name, android.net.ipmemorystore.IOnBlobRetrievedListener listener) throws android.os.RemoteException;
  /**
       * Delete all data because a factory reset operation is in progress.
       */
  public void factoryReset() throws android.os.RemoteException;
}
