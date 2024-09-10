package me.bhamza.example;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.*;
import com.github.unidbg.linux.android.dvm.jni.ProxyDvmObject;

import java.io.File;
import java.time.LocalDate;

public class Signer extends AbstractJni {
    private final AndroidEmulator emulator;
    private final VM dalvikVM;
    private final DalvikModule dalvikModule;
    private final DvmClass dvmMainActivity;
    private final String sign_method_signature;
    private final String soFilePath;

    public Signer(String soFilePath) {
        this.soFilePath = soFilePath;

        this.emulator = AndroidEmulatorBuilder.for64Bit().setProcessName("me.bhamza.hellojni").build();
        this.emulator.getMemory().setLibraryResolver(new AndroidResolver(23));
        this.dalvikVM = emulator.createDalvikVM();
        this.dalvikVM.setJni(this);
        this.dalvikVM.setVerbose(false);
        this.dalvikModule = dalvikVM.loadLibrary(new File(soFilePath), false);

        this.dvmMainActivity = dalvikVM.resolveClass("me/bhamza/hellosignjni/MainActivity");
        this.sign_method_signature = "sign(Ljava/lang/String;)Ljava/lang/String;";
    }

    @Override
    public DvmObject<?> callStaticObjectMethodV(BaseVM vm, DvmClass dvmClass, DvmMethod dvmMethod, VaList vaList) {
        switch (dvmMethod.getSignature()) {
            case "java/time/LocalDate->now()Ljava/time/LocalDate;":
                return ProxyDvmObject.createObject(dalvikVM, LocalDate.now());
        }
        return super.callStaticObjectMethodV(vm, dvmClass, dvmMethod, vaList);
    }

    @Override
    public DvmObject<?> callObjectMethodV(BaseVM vm, DvmObject<?> dvmObject, String signature, VaList vaList) {
        switch (signature) {
            case "java/time/LocalDate->toString()Ljava/lang/String;":
                // System.out.println(dvmObject.getValue().toString()); // print the date
                return new StringObject(dalvikVM, dvmObject.getValue().toString());
        }
        return super.callObjectMethodV(vm, dvmObject, signature, vaList);
    }

    public String sign(String message) {
        DvmObject<?> dvm_message = ProxyDvmObject.createObject(this.dalvikVM, message);
        DvmObject<String> ret_val = dvmMainActivity.callStaticJniMethodObject(emulator, sign_method_signature, dvm_message);
        return ret_val.getValue();
    }

}
