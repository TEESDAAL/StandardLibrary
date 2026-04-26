package base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;
import java.nio.charset.StandardCharsets;
import static base.Util.*;

public interface _TxtRead$0{
  static final HashMap<String,String> cache= new HashMap<>();

  default String read(String path, String diskPath, String zipSteps, String zipEntry){
    var root= System.getProperty("fearlessUser.dir");
    var full= Path.of(root).resolve(diskPath);
    try{
      if (zipEntry.isEmpty()){ return Files.readString(full, StandardCharsets.UTF_8); }
      var steps= zipSteps.isEmpty() ? List.<String>of() : List.of(zipSteps.split(";"));
      var bytes= new ReadZip(
        n->{throw nonDetErr("Files under project root has been altered.\nBad zip entry name: "+n);},
        n->{throw nonDetErr("Files under project root has been altered.\nDuplicate zip entry name: "+n);},
        n->{throw nonDetErr("Files under project root has been altered.\nZip entry too large: "+n);}
        ).readEntry(
        ()->new ZipInputStream(Files.newInputStream(full), StandardCharsets.UTF_8),
        steps,
        zipEntry,
        StandardCharsets.UTF_8
        );
      return new String(bytes, StandardCharsets.UTF_8);
    }
    catch(IOException ioe){
      throw nonDetErr("Files under project root has been altered.\nFile not found in "+path+" "+ioe);
    }
  }
  default Object imm$cacheStrUtf8$4(Object p0, Object p1, Object p2, Object p3){
    String path= ((Str$0Instance)p0).val();
    String diskPath= ((Str$0Instance)p1).val();
    String zipSteps= ((Str$0Instance)p2).val();
    String zipEntry= ((Str$0Instance)p3).val();
    String key= path+"\n"+diskPath+"\n"+zipSteps+"\n"+zipEntry;
    String content= cache.computeIfAbsent(key, _->read(path, diskPath, zipSteps, zipEntry));
    return new Str$0Instance(content);
  }
  _TxtRead$0 instance= new _TxtRead$0(){};
}