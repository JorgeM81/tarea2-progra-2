package File;

import Domain.Car;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class CarFile {
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;//cantidad de registros en el archivo
    private int regSize;//tamaño del registro
    private String myFilePath;//ruta
    //constructor
    public CarFile(File file) throws IOException{
        //se guarda la ruta
        myFilePath = file.getPath();
        //se pone el tamaño maximo
        this.regSize = 150;
        //se valida si existe o no el archivo
        if(file.exists() && !file.isFile()){
            throw new IOException(file.getName() + " is an invalid file");
        }else{
            //de lo contrario se creara un nuevo archivo
            randomAccessFile = new RandomAccessFile(file, "rw");
            //necesitamos indicar cuantos registros tiene el archivo
            this.regsQuantity = 
                    (int)Math.ceil((double)randomAccessFile.length() / (double)regSize);
        }
    }//constructor
    //se cierra el archivo
    public void close() throws IOException{
        randomAccessFile.close();
    }
    //metodo para poder pedir el numero de registros
    public int fileSize(){
        return this.regsQuantity;
    }
    //se hace un metodo para colocar los atributos del auto
    public boolean putValue(Car car) throws IOException{
        if(car.sizeInBytes() > this.regSize){
            System.err.println("2 - Record size is out of bounds");
            return false;
        }else{
            //se escriben los atributos
            car.setSerie(this.regsQuantity);
            randomAccessFile.seek(this.regsQuantity * this.regSize);
            randomAccessFile.writeUTF(car.getName());
            randomAccessFile.writeInt(car.getYear());
            randomAccessFile.writeFloat(car.getMileage());
            randomAccessFile.writeBoolean(car.isAmerican());
            randomAccessFile.writeInt(car.getSerie());
            ++this.regsQuantity;
            return true;
        }
    }//putValue
    public boolean updateValue(int serie, Car car) throws IOException{
        if(car.sizeInBytes() > this.regSize){
            System.err.println("2 - Record size is out of bounds");
            return false;
        }else{
            //se escriben los atributos
            randomAccessFile.seek(serie * this.regSize);
            randomAccessFile.writeUTF(car.getName());
            randomAccessFile.writeInt(car.getYear());
            randomAccessFile.writeFloat(car.getMileage());
            randomAccessFile.writeBoolean(car.isAmerican());
            randomAccessFile.writeInt(car.getSerie());
            ++this.regsQuantity;
            return true;
        }
    }//updateValue
    public Car getCar(int serie) throws IOException{
        //se valida la posicion
        if(serie >= 0 && serie <= this.regsQuantity){
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(serie * this.regSize);
            //se leen los atributos
            Car carTemp = new Car();
            carTemp.setName(randomAccessFile.readUTF());
            carTemp.setYear(randomAccessFile.readInt());
            carTemp.setMileage(randomAccessFile.readFloat());
            carTemp.setAmerican(randomAccessFile.readBoolean());
            carTemp.setSerie(randomAccessFile.readInt());
            //se comprueba si el auto no ha sido eliminado
            if(carTemp.getName().equalsIgnoreCase("deleted")){
                return null;
            }else{
                return carTemp;
            }
        }else{
            System.err.println("3 - position is out of bounds");
            return null;
        }
    }//getCar
    //se crea el metodo para poder eliminar un auto
    public boolean deleteCar(int serie) throws IOException{
        Car myCar;
        //se buscar el carro que se desea eliminar
        for(int i = 0; i < this.regsQuantity; i++){
            //se obtiene un carro
            myCar = this.getCar(i);
            //se comprueba si es el carro a eliminar
            if(myCar.getSerie()==serie){
                //marcar como eliminado
                myCar.setName("deleted");
                return this.putValue(myCar);
            }
        }
        return false;
    }//deleteCar
    //se crea el metodo para comprimir el archivo
    public ArrayList<Car> getAllCars() throws IOException{
        ArrayList<Car> studentsArray = new ArrayList<Car>();
        for(int i = 0; i < this.regsQuantity; i++){
            Car sTemp = this.getCar(i);
            if(sTemp != null){
                studentsArray.add(sTemp);
            }
        }//for
        return studentsArray;
    }//Lista de autos
}
