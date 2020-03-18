package com.dennis.conccurency.chapter11;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 描述：自定义类加载器
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/15 12:24
 */
public class MyClassLoader extends ClassLoader {
    // 定义默认的class存放路径
    private final static Path DEFAULT_CLASS_DIR = Paths.get("E:", "classLoader1");

    private final Path classDir;

    // 使用默认的class路径
    public MyClassLoader() {
        super();
        this.classDir = DEFAULT_CLASS_DIR;
    }

    // 允许传入指定路径
    public MyClassLoader(Path classDir) {
        super();
        this.classDir = classDir;
    }

    // 指定class路径的同时，指定父类加载器
    public MyClassLoader(Path classDir, ClassLoader parent) {
        super(parent);
        this.classDir = classDir;
    }

    // 重写父类的findClass方法
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 读取class的二进制数据
        byte[] bytes = readClassByte(name);
        // 若为空则抛出classNotFoundException
        if (bytes == null) {
            throw new ClassNotFoundException("can not find the class :" + name);
        }
        // 调用defineClass方法定义class
        Class<?> aClass = this.defineClass(name, bytes, 0, bytes.length);
        return aClass;
    }

    // 将class文件读入内存
    private byte[] readClassByte(String name) throws ClassNotFoundException {
        // 将包名分隔符转换为文件分隔符
        String classPath = name.replace(".", "/");
        Path classFullPath = classDir.resolve(Paths.get(classPath + ".class"));
        if (!classFullPath.toFile().exists()) {
            throw new ClassNotFoundException("the class name:" + name + " not found");
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Files.copy(classFullPath, outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ClassNotFoundException("load the class " + name + "occur error:", e);
        }
    }

    @Override
    public String toString() {
        return "MyClassLoader{" +
                "classDir=" + classDir +
                '}';
    }
}
















































