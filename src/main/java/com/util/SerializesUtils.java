/*
 * Copyright (C) 2018 Shanghai Sioo soft Co., Ltd
 *
 * All copyrights reserved by Shanghai Sioo.
 * Any copying, transferring or any other usage is prohibited.
 * Or else, Shanghai Sioo possesses the right to require legal 
 * responsibilities from the violator.
 * All third-party contributions are distributed under license by
 * Shanghai Sioo soft Co., Ltd.
 */
package com.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayOutputStream;

/**
 * 序列化工具类
 * @author hangfy  2018年6月4日 上午11:54:45
 * @since JDK 1.7	
 */
public class SerializesUtils {
	
	private final static ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			kryo = new Kryo();
			kryo.setReferences(false);
			kryo.setRegistrationRequired(false);
			kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
			return kryo;
		};
	};

	public final static byte[] serialize(Object obj) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		try {
			kryos.get().writeClassAndObject(output, obj);
		} finally {
			output.close();
		}
		return baos.toByteArray();
	}

	public final static Object deserialize(byte[] objectData) throws Exception {
		Input input = new Input(objectData);
		try {
			return kryos.get().readClassAndObject(input);
		} finally {
			input.close();
		}
	}

}
