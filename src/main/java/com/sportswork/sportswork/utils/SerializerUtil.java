package com.sportswork.sportswork.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * object对象序列化
 * @author SDT13843
 *
 */
public class SerializerUtil implements Serializable {

	private static final Logger log = LogManager.getLogger(SerializerUtil.class);
	private static final long serialVersionUID = 1887903823461742529L;

	public static byte[] objectToByte(Object obj) {
		byte[] bytes = new byte[1024];
		try {
			// object to bytearray
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);

			bytes = bo.toByteArray();

			bo.close();
			oo.close();
		} catch (Exception e) {
			log.error("translation:", e);
		}
		return (bytes);
	}

	public static Object byteToObject(byte[] bytes) {
		Object obj = new Object();
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		try {
			// bytearray to object
			if(bytes == null) {
				return null;
			}
			bi = new ByteArrayInputStream(bytes);
			oi = new ObjectInputStream(bi);

			obj = oi.readObject();
		} catch (Exception e) {
			log.error("translation:", e);
		} finally {
        	try {
        		if(bi != null) {
        			bi.close();
        		}
        		if(oi != null) {
        			oi.close();
        		}
			} catch (IOException e) {
			}  
        }  
		
		return obj;
	}
	
	public static <T> byte[] serializeList(List<T> value) {
        byte[] rv=null;  
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {  
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for(T object : value){  
                os.writeObject(object);  
            }  
            os.writeObject(null);  
            rv = bos.toByteArray();  
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {  
        	try {
        		if(os!=null) {
        			os.close();
        		}
        		if(bos != null) {
        			bos.close();
        		}
			} catch (IOException e) {
			}  
        }  
        return rv;  
    }  
	public static <T> byte[] serializeList(Set<T> value) {
        byte[] rv=null;  
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {  
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for(T object : value){  
                os.writeObject(object);  
            }  
            os.writeObject(null);  
            rv = bos.toByteArray();  
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {  
        	try {
        		if(os!=null) {
        			os.close();
        		}
        		if(bos != null) {
        			bos.close();
        		}
			} catch (IOException e) {
			}  
        }  
        return rv;  
    }

    @SuppressWarnings("unchecked")
	public static <T> List<T> deserializeList(byte[] in) {
        List<T> list = new ArrayList<T>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {  
            if(in != null) {
                bis=new ByteArrayInputStream(in);
                is=new ObjectInputStream(bis);
                while (true) {  
                	Object object = is.readObject();
                    if(object == null){  
                        break;  
                    }else{  
                        list.add((T)object);  
                    }  
                }  
            }  
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        } finally {
        	try {
        		if(is != null) {
        			is.close();
        		}
        		if(bis != null) {
        			bis.close();
        		}
			} catch (IOException e) {
			}  
        }  
        return list;  
    }  
    @SuppressWarnings("unchecked")
    public static <T> Set<T> deserializeSet(byte[] in) {
    	Set<T> list = new HashSet<>();
    	ByteArrayInputStream bis = null;
    	ObjectInputStream is = null;
    	try {  
    		if(in != null) {
    			bis=new ByteArrayInputStream(in);
    			is=new ObjectInputStream(bis);
    			while (true) {  
    				Object object = is.readObject();
    				if(object == null){  
    					break;  
    				}else{  
    					list.add((T)object);  
    				}  
    			}  
    		}  
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if(is != null) {
    				is.close();
    			}
    			if(bis != null) {
    				bis.close();
    			}
    		} catch (IOException e) {
    		}  
    	}  
    	return list;  
    }  
}