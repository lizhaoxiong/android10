/*___Generated_by_IDEA___*/

/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package android.car.storagemonitoring;
/** @hide */
public interface IIoStatsListener extends android.os.IInterface
{
  /** Default implementation for IIoStatsListener. */
  public static class Default implements android.car.storagemonitoring.IIoStatsListener
  {
    /**
         * Called each time a new uid_io activity snapshot is generated by the service.
         *
         * The interval at which new snapshots are generated is an OEM-configurable property.
         */
    @Override public void onSnapshot(android.car.storagemonitoring.IoStats snapshot) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements android.car.storagemonitoring.IIoStatsListener
  {
    private static final java.lang.String DESCRIPTOR = "android.car.storagemonitoring.IIoStatsListener";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an android.car.storagemonitoring.IIoStatsListener interface,
     * generating a proxy if needed.
     */
    public static android.car.storagemonitoring.IIoStatsListener asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof android.car.storagemonitoring.IIoStatsListener))) {
        return ((android.car.storagemonitoring.IIoStatsListener)iin);
      }
      return new android.car.storagemonitoring.IIoStatsListener.Stub.Proxy(obj);
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
        case TRANSACTION_onSnapshot:
        {
          data.enforceInterface(descriptor);
          android.car.storagemonitoring.IoStats _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.car.storagemonitoring.IoStats.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          this.onSnapshot(_arg0);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements android.car.storagemonitoring.IIoStatsListener
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
           * Called each time a new uid_io activity snapshot is generated by the service.
           *
           * The interval at which new snapshots are generated is an OEM-configurable property.
           */
      @Override public void onSnapshot(android.car.storagemonitoring.IoStats snapshot) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((snapshot!=null)) {
            _data.writeInt(1);
            snapshot.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_onSnapshot, _data, null, android.os.IBinder.FLAG_ONEWAY);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onSnapshot(snapshot);
            return;
          }
        }
        finally {
          _data.recycle();
        }
      }
      public static android.car.storagemonitoring.IIoStatsListener sDefaultImpl;
    }
    static final int TRANSACTION_onSnapshot = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    public static boolean setDefaultImpl(android.car.storagemonitoring.IIoStatsListener impl) {
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
    public static android.car.storagemonitoring.IIoStatsListener getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
       * Called each time a new uid_io activity snapshot is generated by the service.
       *
       * The interval at which new snapshots are generated is an OEM-configurable property.
       */
  public void onSnapshot(android.car.storagemonitoring.IoStats snapshot) throws android.os.RemoteException;
}