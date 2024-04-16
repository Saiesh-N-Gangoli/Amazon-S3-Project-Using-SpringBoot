import React, { useState } from 'react'
import './ImageUploader.css'
import image from '../components/images/img.png'
import axios from 'axios' 
import Navigation from './NavigationBar/Navigation'
import { ToastContainer, toast } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';
import { Spinner } from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css';


const ImageUploader = () => {
    const [selectedImage, setSelectedImage] = useState(null);
    const [uploading, setUploading] = useState(false);
 
    const fileHandle = (event) =>{
        const file =  event.target.files[0];
        console.log(file)
        if (file.type === "image/png" || file.type === "image/jpeg") {
            setSelectedImage(file);
        } else{
            alert("Choose Image Files Only!!");
            event.target.value = '';
            setSelectedImage(null);
        }
    }

    const uploadFile = (event) => {
        event.preventDefault();
        console.log("Im clicked")
        if(selectedImage){
            uploadImagetoS3Bucket();
            console.log("I am called");
            toast.success("Image uploaded successfully");
            clearImage();
        }else{
            toast.error("Select Image First");
        }
    }

    const uploadImagetoS3Bucket = () =>{
        const url = `http://localhost:8080/api/uploadimage`;
        const data = new FormData();
        data.append("multipartFile", selectedImage);
        setUploading(true);
        axios.post(url, data)
        .then(
            (response) =>{
                console.log("Response Data: "+response.data);
                document.querySelector('.file-css').value = '';
            }
        )
        .catch(
            (error) =>{
                console.log(error);
            }
        )
        .finally(
           () => {
            console.log("File uploaded successfully");
            setTimeout(() => {
                setUploading(false);
            }, 400);
           }
        )
    }

    const clearImage = () =>{
        setSelectedImage(null);
    }


  return (
    <div>
     <Navigation/>
     <div className="containerz">
     <img src={image} alt="" className='image-css'/>
        <form action="" className='container-form'>
        <input type="file" className='file-css' onChange={fileHandle}/>
       <div className='flex-in'>
       <input type="button" value="Upload" className='button-css' onClick={uploadFile}/>
       <input type="reset" value="Clear" className='button-css-clear' onClick={clearImage}/>
       </div>
        </form>
        {uploading
        &&
        <div className='spinner-class'>
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
          </Spinner>
          <p>Uploading...</p>
        </div>
        }
     </div>
     <ToastContainer/>
    </div>
  )
}

export default ImageUploader
