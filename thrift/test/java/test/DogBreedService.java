/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package test;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apache.thrift.*;
import org.apache.thrift.async.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

import com.twitter.scrooge.TReusableBuffer;
import com.twitter.scrooge.TReusableMemoryTransport;
import com.twitter.util.Future;
import com.twitter.util.Function;
import com.twitter.util.Function2;
import com.twitter.util.Try;
import com.twitter.util.Return;
import com.twitter.util.Throw;
import com.twitter.finagle.thrift.DeserializeCtx;
import com.twitter.finagle.thrift.ThriftClientRequest;

public class DogBreedService {
  public interface Iface {
    public BreedInfoResponse breedInfo(String breedName) throws TException;
  }

  public interface AsyncIface {
    public void breedInfo(String breedName, AsyncMethodCallback<BreedInfoResponse> resultHandler) throws TException;
  }

  public interface ServiceIface {
    public Future<BreedInfoResponse> breedInfo(String breedName);
  }

  public static class Client extends TServiceClient implements Iface {
    public static class Factory implements TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(TProtocol iprot, TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(TProtocol prot)
    {
      this(prot, prot);
    }

    public Client(TProtocol iprot, TProtocol oprot)
    {
      super(iprot, oprot);
    }

    public BreedInfoResponse breedInfo(String breedName) throws TException
    {
      send_breedInfo(breedName);
      return recv_breedInfo();
    }

    public void send_breedInfo(String breedName) throws TException
    {
      oprot_.writeMessageBegin(new TMessage("breedInfo", TMessageType.CALL, ++seqid_));
      breedInfo_args __args__ = new breedInfo_args();
      __args__.setBreedName(breedName);
      __args__.write(oprot_);
      oprot_.writeMessageEnd();
      oprot_.getTransport().flush();
    }

    public BreedInfoResponse recv_breedInfo() throws TException
    {
      TMessage msg = iprot_.readMessageBegin();
      if (msg.type == TMessageType.EXCEPTION) {
        TApplicationException x = TApplicationException.readFrom(iprot_);
        iprot_.readMessageEnd();
        throw x;
      }
      if (msg.seqid != seqid_) {
        throw new TApplicationException(TApplicationException.BAD_SEQUENCE_ID, "breedInfo failed: out of sequence response");
      }
      breedInfo_result result = new breedInfo_result();
      result.read(iprot_);
      iprot_.readMessageEnd();
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new TApplicationException(TApplicationException.MISSING_RESULT, "breedInfo failed: unknown result");
    }
  }

  public static class AsyncClient extends TAsyncClient implements AsyncIface {
    public static class Factory implements TAsyncClientFactory<AsyncClient> {
      private final TAsyncClientManager clientManager;
      private final TProtocolFactory protocolFactory;
      public Factory(TAsyncClientManager clientManager, TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    private final TNonblockingTransport transport;
    private final TAsyncClientManager manager;

    public AsyncClient(TProtocolFactory protocolFactory, TAsyncClientManager clientManager, TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
      this.manager = clientManager;
      this.transport = transport;
    }

    public void breedInfo(String breedName, AsyncMethodCallback<BreedInfoResponse> __resultHandler__) throws TException {
      checkReady();
      breedInfo_call __method_call__ = new breedInfo_call(breedName, __resultHandler__, this, super.getProtocolFactory(), this.transport);
      this.manager.call(__method_call__);
    }

    public static class breedInfo_call extends TAsyncMethodCall<BreedInfoResponse> {
      private String breedName;

      public breedInfo_call(String breedName, AsyncMethodCallback<BreedInfoResponse> __resultHandler__, TAsyncClient __client__, TProtocolFactory __protocolFactory__, TNonblockingTransport __transport__) throws TException {
        super(__client__, __protocolFactory__, __transport__, __resultHandler__, false);
        this.breedName = breedName;
      }

      public void write_args(TProtocol __prot__) throws TException {
        __prot__.writeMessageBegin(new TMessage("breedInfo", TMessageType.CALL, 0));
        breedInfo_args __args__ = new breedInfo_args();
        __args__.setBreedName(breedName);
        __args__.write(__prot__);
        __prot__.writeMessageEnd();
      }

      protected BreedInfoResponse getResult() throws TException {
        if (getState() != State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        TMemoryInputTransport __memoryTransport__ = new TMemoryInputTransport(getFrameBuffer().array());
        TProtocol __prot__ = super.client.getProtocolFactory().getProtocol(__memoryTransport__);
        return (new Client(__prot__)).recv_breedInfo();
      }
     }
   }


  public static class ServiceToClient implements ServiceIface {
    private final com.twitter.finagle.Service<ThriftClientRequest, byte[]> service;
    private final TProtocolFactory protocolFactory;
    private final TReusableBuffer tlReusableBuffer;
    private final scala.PartialFunction<com.twitter.finagle.service.ReqRep,com.twitter.finagle.service.ResponseClass> responseClassifier;

    /**
     * @deprecated use {@link com.twitter.finagle.thrift.RichClientParam} instead
     */
    @Deprecated
    public ServiceToClient(com.twitter.finagle.Service<ThriftClientRequest, byte[]> service, TProtocolFactory protocolFactory, scala.PartialFunction<com.twitter.finagle.service.ReqRep,com.twitter.finagle.service.ResponseClass> responseClassifier) {
      this(service, new com.twitter.finagle.thrift.RichClientParam(protocolFactory, responseClassifier));
    }

    public ServiceToClient(com.twitter.finagle.Service<ThriftClientRequest, byte[]> service, com.twitter.finagle.thrift.RichClientParam clientParam) {
      
      this.service = service;
      this.protocolFactory = clientParam.restrictedProtocolFactory();
      this.responseClassifier = clientParam.responseClassifier();
      this.tlReusableBuffer = new TReusableBuffer(512, clientParam.maxThriftBufferSize());
    }

    public ServiceToClient(com.twitter.finagle.Service<ThriftClientRequest, byte[]> service) {
      this(service, new com.twitter.finagle.thrift.RichClientParam());
    }

    /**
     * @deprecated use {@link com.twitter.finagle.thrift.RichClientParam} instead
     */
    @Deprecated
    public ServiceToClient(com.twitter.finagle.Service<ThriftClientRequest, byte[]> service, TProtocolFactory protocolFactory) {
      this(service, new com.twitter.finagle.thrift.RichClientParam(protocolFactory, com.twitter.finagle.service.ResponseClassifier.Default()));
    }

    public Future<BreedInfoResponse> breedInfo(String breedName) {
      try {
        TReusableMemoryTransport __memoryTransport__ = tlReusableBuffer.get();
        TProtocol __prot__ = this.protocolFactory.getProtocol(__memoryTransport__);
        __prot__.writeMessageBegin(new TMessage("breedInfo", TMessageType.CALL, 0));
        breedInfo_args __args__ = new breedInfo_args();
        __args__.setBreedName(breedName);
        __args__.write(__prot__);
        __prot__.writeMessageEnd();


        byte[] __buffer__ = Arrays.copyOf(__memoryTransport__.getArray(), __memoryTransport__.length());
        final ThriftClientRequest __request__ = new ThriftClientRequest(__buffer__, false);

        Function<byte[], com.twitter.util.Try<BreedInfoResponse>> replyDeserializer =
          new Function<byte[], com.twitter.util.Try<BreedInfoResponse>>() {
            public com.twitter.util.Try<BreedInfoResponse> apply(byte[] __buffer__) {
              TMemoryInputTransport __memoryTransport__ = new TMemoryInputTransport(__buffer__);
              TProtocol __prot__ = ServiceToClient.this.protocolFactory.getProtocol(__memoryTransport__);
              try {
                return new com.twitter.util.Return<BreedInfoResponse>(((new Client(__prot__)).recv_breedInfo()));
              } catch (Exception e) {
                return new com.twitter.util.Throw<BreedInfoResponse>(e);
              }
            }
          };
        DeserializeCtx serdeCtx = new DeserializeCtx<BreedInfoResponse>(__args__, replyDeserializer);

        return com.twitter.finagle.context.Contexts.local().let(
          DeserializeCtx.Key(),
          serdeCtx,
          new com.twitter.util.Function0<Future<BreedInfoResponse>>() {
            public Future<BreedInfoResponse> apply() {

              Future<byte[]> __done__ = service.apply(__request__);
              return __done__.flatMap(new Function<byte[], Future<BreedInfoResponse>>() {
                public Future<BreedInfoResponse> apply(byte[] __buffer__) {
                  TMemoryInputTransport __memoryTransport__ = new TMemoryInputTransport(__buffer__);
                  TProtocol __prot__ = ServiceToClient.this.protocolFactory.getProtocol(__memoryTransport__);
                  try {
                    return Future.value((new Client(__prot__)).recv_breedInfo());
                  } catch (Exception e) {
                    return Future.exception(e);
                  }
                }
              });
            }
          });
      } catch (TException e) {
        return Future.exception(e);
      } finally {
        tlReusableBuffer.reset();
      }
    }
  }

  public static class Processor implements TProcessor {
    public Processor(Iface iface)
    {
      iface_ = iface;
      processMap_.put("breedInfo", new breedInfo());
    }

    protected static interface ProcessFunction {
      public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException;
    }

    private Iface iface_;
    protected final HashMap<String,ProcessFunction> processMap_ = new HashMap<String,ProcessFunction>();

    public boolean process(TProtocol iprot, TProtocol oprot) throws TException
    {
      TMessage msg = iprot.readMessageBegin();
      ProcessFunction fn = processMap_.get(msg.name);
      if (fn == null) {
        TProtocolUtil.skip(iprot, TType.STRUCT);
        iprot.readMessageEnd();
        TApplicationException x = new TApplicationException(TApplicationException.UNKNOWN_METHOD, "Invalid method name: '"+msg.name+"'");
        oprot.writeMessageBegin(new TMessage(msg.name, TMessageType.EXCEPTION, msg.seqid));
        x.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
        return true;
      }
      fn.process(msg.seqid, iprot, oprot);
      return true;
    }

    private class breedInfo implements ProcessFunction {
      public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException
      {
        breedInfo_args args = new breedInfo_args();
        try {
          args.read(iprot);
        } catch (TProtocolException e) {
          iprot.readMessageEnd();
          TApplicationException x = new TApplicationException(TApplicationException.PROTOCOL_ERROR, e.getMessage());
          oprot.writeMessageBegin(new TMessage("breedInfo", TMessageType.EXCEPTION, seqid));
          x.write(oprot);
          oprot.writeMessageEnd();
          oprot.getTransport().flush();
          return;
        }
        iprot.readMessageEnd();
        breedInfo_result result = new breedInfo_result();
        result.success = iface_.breedInfo(args.breedName);
        
        oprot.writeMessageBegin(new TMessage("breedInfo", TMessageType.REPLY, seqid));
        result.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
      }
    }
  }

  public static class Service extends com.twitter.finagle.Service<byte[], byte[]> {
    private final ServiceIface iface;
    private final TProtocolFactory protocolFactory;
    private final TReusableBuffer tlReusableBuffer;
    protected HashMap<String, com.twitter.finagle.Service<scala.Tuple2<TProtocol, Integer>, byte[]>> serviceMap =
      new HashMap<String, com.twitter.finagle.Service<scala.Tuple2<TProtocol, Integer>, byte[]>>();
    public Service(final ServiceIface iface, final com.twitter.finagle.thrift.RichServerParam serverParam) {
      this.iface = iface;
      this.protocolFactory = serverParam.restrictedProtocolFactory();
      this.tlReusableBuffer = new TReusableBuffer(512, serverParam.maxThriftBufferSize());
      createMethods();
    }

    public Service(final ServiceIface iface) {
      this(iface, new com.twitter.finagle.thrift.RichServerParam());
    }

    /**
     * @deprecated use {@link com.twitter.finagle.thrift.RichServerParam} instead
     */
    @Deprecated
    public Service(final ServiceIface iface, final TProtocolFactory protocolFactory) {
      this(iface, new com.twitter.finagle.thrift.RichServerParam(protocolFactory));
    }

    private void createMethods() {

      class breedInfoService {
        private final com.twitter.finagle.SimpleFilter<scala.Tuple2<TProtocol, Integer>, byte[]> protocolExnFilter = new com.twitter.finagle.SimpleFilter<scala.Tuple2<TProtocol, Integer>, byte[]>() {
          @Override
          public Future<byte[]> apply(scala.Tuple2<TProtocol, Integer> request, com.twitter.finagle.Service<scala.Tuple2<TProtocol, Integer>, byte[]> service) {
            return service.apply(request).rescue(new Function<Throwable, Future<byte[]>>() {
              @Override
              public Future<byte[]> apply(Throwable e) {
                TProtocol iprot = request._1();
                Integer seqid = request._2();
                if (e instanceof TProtocolException) {
                  try {
                    iprot.readMessageEnd();
                    return exception("breedInfo", seqid, TApplicationException.PROTOCOL_ERROR, e.getMessage());
                  } catch (Exception e1) {
                    return Future.exception(e1);
                  }
                } else {
                  return Future.exception(e);
                }
              }
            });
          }
        };

        private final com.twitter.finagle.Filter<scala.Tuple2<TProtocol, Integer>, byte[], breedInfo_args, BreedInfoResponse> serdeFilter = new com.twitter.finagle.Filter<scala.Tuple2<TProtocol, Integer>, byte[], breedInfo_args, BreedInfoResponse>() {
          @Override
          public Future<byte[]> apply(scala.Tuple2<TProtocol, Integer> request, com.twitter.finagle.Service<breedInfo_args, BreedInfoResponse> service) {
            TProtocol iprot = request._1();
            Integer seqid = request._2();
            breedInfo_args args = new breedInfo_args();
            try {
              args.read(iprot);
              iprot.readMessageEnd();
            } catch (Exception e) {
              return Future.exception(e);
            }

            Future<BreedInfoResponse> res = service.apply(args);
            breedInfo_result result = new breedInfo_result();
            return res.flatMap(new Function<BreedInfoResponse, Future<byte[]>>() {
              @Override
              public Future<byte[]> apply(BreedInfoResponse value) {
                result.success = value;
                result.setSuccessIsSet(true);
                return reply("breedInfo", seqid, result);
              }
            }).rescue(new Function<Throwable, Future<byte[]>>() {
              @Override
              public Future<byte[]> apply(Throwable t) {
                return Future.exception(t);
              }
            });
          }
        };

        private final com.twitter.finagle.Service<breedInfo_args, BreedInfoResponse> methodService = new com.twitter.finagle.Service<breedInfo_args, BreedInfoResponse>() {
          @Override
          public Future<BreedInfoResponse> apply(breedInfo_args args) {
            Future<BreedInfoResponse> future = iface.breedInfo(args.breedName);
            return future;
          }
        };

        private final com.twitter.finagle.Service<scala.Tuple2<TProtocol, Integer>, byte[]> getService =
          protocolExnFilter.andThen(serdeFilter).andThen(methodService);
      }

      serviceMap.put("breedInfo", (new breedInfoService()).getService);
    }

    public Future<byte[]> apply(byte[] request) {
      TTransport inputTransport = new TMemoryInputTransport(request);
      TProtocol iprot = protocolFactory.getProtocol(inputTransport);

      TMessage msg;
      try {
        msg = iprot.readMessageBegin();
      } catch (Exception e) {
        return Future.exception(e);
      }

      com.twitter.finagle.Service<scala.Tuple2<TProtocol, Integer>, byte[]> svc = serviceMap.get(msg.name);
      if (svc == null) {
        try {
          TProtocolUtil.skip(iprot, TType.STRUCT);
          iprot.readMessageEnd();
          TApplicationException x = new TApplicationException(TApplicationException.UNKNOWN_METHOD, "Invalid method name: '"+msg.name+"'");
          TReusableMemoryTransport memoryBuffer = tlReusableBuffer.get();
          TProtocol oprot = protocolFactory.getProtocol(memoryBuffer);
          oprot.writeMessageBegin(new TMessage(msg.name, TMessageType.EXCEPTION, msg.seqid));
          x.write(oprot);
          oprot.writeMessageEnd();
          oprot.getTransport().flush();
          return Future.value(Arrays.copyOf(memoryBuffer.getArray(), memoryBuffer.length()));
        } catch (Exception e) {
          return Future.exception(e);
        } finally {
          tlReusableBuffer.reset();
        }
      }

      return svc.apply(new scala.Tuple2(iprot, msg.seqid));
    }

    private Future<byte[]> reply(String name, Integer seqid, TBase result) {
      try {
        TReusableMemoryTransport memoryBuffer = tlReusableBuffer.get();
        TProtocol oprot = protocolFactory.getProtocol(memoryBuffer);

        oprot.writeMessageBegin(new TMessage(name, TMessageType.REPLY, seqid));
        result.write(oprot);
        oprot.writeMessageEnd();

        return Future.value(Arrays.copyOf(memoryBuffer.getArray(), memoryBuffer.length()));
      } catch (Exception e) {
        return Future.exception(e);
      } finally {
        tlReusableBuffer.reset();
      }
    }

    private Future<byte[]> exception(String name, Integer seqid, Integer code, String message) {
      try {
        TApplicationException x = new TApplicationException(code, message);
        TReusableMemoryTransport memoryBuffer = tlReusableBuffer.get();
        TProtocol oprot = protocolFactory.getProtocol(memoryBuffer);

        oprot.writeMessageBegin(new TMessage(name, TMessageType.EXCEPTION, seqid));
        x.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
        byte[] buffer = Arrays.copyOf(memoryBuffer.getArray(), memoryBuffer.length());
        return Future.value(buffer);
      } catch (Exception e1) {
        return Future.exception(e1);
      } finally {
        tlReusableBuffer.reset();
      }
    }
  }

  public static class breedInfo_args implements TBase<breedInfo_args, breedInfo_args._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("breedInfo_args");

  private static final TField BREED_NAME_FIELD_DESC = new TField("breedName", TType.STRING, (short)1);


  public String breedName;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    BREED_NAME((short)1, "breedName");
  
    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();
  
    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }
  
    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // BREED_NAME
          return BREED_NAME;
        default:
          return null;
      }
    }
  
    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }
  
    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }
  
    private final short _thriftId;
    private final String _fieldName;
  
    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }
  
    public short getThriftFieldId() {
      return _thriftId;
    }
  
    public String getFieldName() {
      return _fieldName;
    }
  }


  // isset id assignments

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  
  /**
   * FieldValueMetaData.type returns TType.STRING for both string and binary field values.
   * This set can be used to determine if a FieldValueMetaData with type TType.STRING is actually
   * declared as binary in the idl file.
   */
  public static final Set<FieldValueMetaData> binaryFieldValueMetaDatas;
  
  private static FieldValueMetaData registerBinaryFieldValueMetaData(FieldValueMetaData f, Set<FieldValueMetaData> binaryFieldValues) {
    binaryFieldValues.add(f);
    return f;
  }
  
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    Set<FieldValueMetaData> tmpSet = new HashSet<FieldValueMetaData>();
    tmpMap.put(_Fields.BREED_NAME, new FieldMetaData("breedName", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    binaryFieldValueMetaDatas = Collections.unmodifiableSet(tmpSet);
    FieldMetaData.addStructMetaDataMap(breedInfo_args.class, metaDataMap);
  }

  /**
   * Returns a map of the annotations and their values for this struct declaration.
   * See fieldAnnotations or valueAnnotations for the annotations attached to struct fields
   * or enum values.
   */
  public static final Map<String, String> structAnnotations;
  static {
    structAnnotations = Collections.emptyMap();
  }

  /**
   * Returns a map of the annotations for each of this struct's fields, keyed by the field.
   * See structAnnotations for the annotations attached to this struct's declaration.
   */
  public static final Map<_Fields, Map<String, String>> fieldAnnotations;
  static {
    fieldAnnotations = Collections.emptyMap();
  }

  /**
   * Returns the set of fields that have a configured default value.
   * The default values for these fields can be obtained by
   * instantiating this class with the default constructor.
   */
  public static final Set<_Fields> hasDefaultValue;
  static {
    Set<_Fields> tmp = EnumSet.noneOf(_Fields.class);
    hasDefaultValue = Collections.unmodifiableSet(tmp);
  }


  public breedInfo_args() {
  }

  public breedInfo_args(
    String breedName)
  {
    this();
    this.breedName = breedName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public breedInfo_args(breedInfo_args other) {
    if (other.isSetBreedName()) {
      this.breedName = other.breedName;
    }
  }

  public static List<String> validateNewInstance(breedInfo_args item) {
    final List<String> buf = new ArrayList<String>();

    return buf;
  }

  public breedInfo_args deepCopy() {
    return new breedInfo_args(this);
  }

  @java.lang.Override
  public void clear() {
    this.breedName = null;
  }

  public String getBreedName() {
    return this.breedName;
  }

  public breedInfo_args setBreedName(String breedName) {
    this.breedName = breedName;
    
    return this;
  }

  public void unsetBreedName() {
    this.breedName = null;
  }

  /** Returns true if field breedName is set (has been assigned a value) and false otherwise */
  public boolean isSetBreedName() {
    return this.breedName != null;
  }

  public void setBreedNameIsSet(boolean value) {
    if (!value) {
      this.breedName = null;
    }
  }

  @SuppressWarnings("unchecked")
  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BREED_NAME:
      if (value == null) {
        unsetBreedName();
      } else {
        setBreedName((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BREED_NAME:
      return getBreedName();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BREED_NAME:
      return isSetBreedName();
    }
    throw new IllegalStateException();
  }

  @java.lang.Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof breedInfo_args)
      return this.equals((breedInfo_args)that);
    return false;
  }

  public boolean equals(breedInfo_args that) {
    if (that == null)
      return false;
    boolean this_present_breedName = true && this.isSetBreedName();
    boolean that_present_breedName = true && that.isSetBreedName();
    if (this_present_breedName || that_present_breedName) {
      if (!(this_present_breedName && that_present_breedName))
        return false;
      if (!this.breedName.equals(that.breedName))
        return false;
    }

    return true;
  }

  @java.lang.Override
  public int hashCode() {
    int hashCode = 1;
    if (isSetBreedName()) {
      hashCode = 31 * hashCode + breedName.hashCode();
    }
    return hashCode;
  }

  public int compareTo(breedInfo_args other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    breedInfo_args typedOther = (breedInfo_args)other;

    lastComparison = Boolean.valueOf(isSetBreedName()).compareTo(typedOther.isSetBreedName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBreedName()) {
      lastComparison = TBaseHelper.compareTo(this.breedName, typedOther.breedName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) {
        break;
      }
      switch (field.id) {
        case 1: // BREED_NAME
          if (field.type == TType.STRING) {
            this.breedName = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();
    
    oprot.writeStructBegin(STRUCT_DESC);
    if (this.breedName != null) {
      oprot.writeFieldBegin(BREED_NAME_FIELD_DESC);
      oprot.writeString(this.breedName);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @java.lang.Override
  public String toString() {
    StringBuilder sb = new StringBuilder("breedInfo_args(");
    boolean first = true;
    sb.append("breedName:");
    if (this.breedName == null) {
      sb.append("null");
    } else {
      sb.append(this.breedName);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}


  public static class breedInfo_result implements TBase<breedInfo_result, breedInfo_result._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("breedInfo_result");

  private static final TField SUCCESS_FIELD_DESC = new TField("success", TType.STRUCT, (short)0);


  public BreedInfoResponse success;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    SUCCESS((short)0, "success");
  
    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();
  
    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }
  
    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 0: // SUCCESS
          return SUCCESS;
        default:
          return null;
      }
    }
  
    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }
  
    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }
  
    private final short _thriftId;
    private final String _fieldName;
  
    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }
  
    public short getThriftFieldId() {
      return _thriftId;
    }
  
    public String getFieldName() {
      return _fieldName;
    }
  }


  // isset id assignments

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  
  /**
   * FieldValueMetaData.type returns TType.STRING for both string and binary field values.
   * This set can be used to determine if a FieldValueMetaData with type TType.STRING is actually
   * declared as binary in the idl file.
   */
  public static final Set<FieldValueMetaData> binaryFieldValueMetaDatas;
  
  private static FieldValueMetaData registerBinaryFieldValueMetaData(FieldValueMetaData f, Set<FieldValueMetaData> binaryFieldValues) {
    binaryFieldValues.add(f);
    return f;
  }
  
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    Set<FieldValueMetaData> tmpSet = new HashSet<FieldValueMetaData>();
    tmpMap.put(_Fields.SUCCESS, new FieldMetaData("success", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, BreedInfoResponse.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    binaryFieldValueMetaDatas = Collections.unmodifiableSet(tmpSet);
    FieldMetaData.addStructMetaDataMap(breedInfo_result.class, metaDataMap);
  }

  /**
   * Returns a map of the annotations and their values for this struct declaration.
   * See fieldAnnotations or valueAnnotations for the annotations attached to struct fields
   * or enum values.
   */
  public static final Map<String, String> structAnnotations;
  static {
    structAnnotations = Collections.emptyMap();
  }

  /**
   * Returns a map of the annotations for each of this struct's fields, keyed by the field.
   * See structAnnotations for the annotations attached to this struct's declaration.
   */
  public static final Map<_Fields, Map<String, String>> fieldAnnotations;
  static {
    fieldAnnotations = Collections.emptyMap();
  }

  /**
   * Returns the set of fields that have a configured default value.
   * The default values for these fields can be obtained by
   * instantiating this class with the default constructor.
   */
  public static final Set<_Fields> hasDefaultValue;
  static {
    Set<_Fields> tmp = EnumSet.noneOf(_Fields.class);
    hasDefaultValue = Collections.unmodifiableSet(tmp);
  }


  public breedInfo_result() {
  }

  public breedInfo_result(
    BreedInfoResponse success)
  {
    this();
    this.success = success;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public breedInfo_result(breedInfo_result other) {
    if (other.isSetSuccess()) {
      this.success = new BreedInfoResponse(other.success);
    }
  }

  public static List<String> validateNewInstance(breedInfo_result item) {
    final List<String> buf = new ArrayList<String>();

    if (item.isSetSuccess()) {
      BreedInfoResponse _success = item.success;
      buf.addAll(test.BreedInfoResponse.validateNewInstance(_success));
    }

    return buf;
  }

  public breedInfo_result deepCopy() {
    return new breedInfo_result(this);
  }

  @java.lang.Override
  public void clear() {
    this.success = null;
  }

  public BreedInfoResponse getSuccess() {
    return this.success;
  }

  public breedInfo_result setSuccess(BreedInfoResponse success) {
    this.success = success;
    
    return this;
  }

  public void unsetSuccess() {
    this.success = null;
  }

  /** Returns true if field success is set (has been assigned a value) and false otherwise */
  public boolean isSetSuccess() {
    return this.success != null;
  }

  public void setSuccessIsSet(boolean value) {
    if (!value) {
      this.success = null;
    }
  }

  @SuppressWarnings("unchecked")
  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUCCESS:
      if (value == null) {
        unsetSuccess();
      } else {
        setSuccess((BreedInfoResponse)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUCCESS:
      return getSuccess();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUCCESS:
      return isSetSuccess();
    }
    throw new IllegalStateException();
  }

  @java.lang.Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof breedInfo_result)
      return this.equals((breedInfo_result)that);
    return false;
  }

  public boolean equals(breedInfo_result that) {
    if (that == null)
      return false;
    boolean this_present_success = true && this.isSetSuccess();
    boolean that_present_success = true && that.isSetSuccess();
    if (this_present_success || that_present_success) {
      if (!(this_present_success && that_present_success))
        return false;
      if (!this.success.equals(that.success))
        return false;
    }

    return true;
  }

  @java.lang.Override
  public int hashCode() {
    int hashCode = 1;
    if (isSetSuccess()) {
      hashCode = 31 * hashCode + success.hashCode();
    }
    return hashCode;
  }

  public int compareTo(breedInfo_result other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    breedInfo_result typedOther = (breedInfo_result)other;

    lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuccess()) {
      lastComparison = TBaseHelper.compareTo(this.success, typedOther.success);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) {
        break;
      }
      switch (field.id) {
        case 0: // SUCCESS
          if (field.type == TType.STRUCT) {
            this.success = new BreedInfoResponse();
            this.success.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    oprot.writeStructBegin(STRUCT_DESC);
    if (this.isSetSuccess()) {
      oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
      this.success.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @java.lang.Override
  public String toString() {
    StringBuilder sb = new StringBuilder("breedInfo_result(");
    boolean first = true;
    sb.append("success:");
    if (this.success == null) {
      sb.append("null");
    } else {
      sb.append(this.success);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}



}
