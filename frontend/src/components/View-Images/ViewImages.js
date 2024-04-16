import React, { useEffect, useState } from 'react'
import axios from 'axios';
import Navigation from '../NavigationBar/Navigation';
import './ViewImages.css'
import { TfiFaceSad } from 'react-icons/tfi';
import { Spinner } from 'react-bootstrap';
import Swal from 'sweetalert2';

const ViewImages = () => {
    const[getFiles, setGetFiles] = useState([]);
    const[loading, setLoading] = useState(true);

    useEffect(()=>{
       fileDisplay();
    },[])

    const fileDisplay = () =>{
        const URL = `http://localhost:8080/api/getAll`
        axios.get(URL)
        .then((response) =>{
            setGetFiles(response.data);
            console.log(response.data);
        }
        )
        .catch((error)=>{
            console.log(error);
        })
        .finally(()=>{
            setTimeout(() => {
                setLoading(false)
            }, 2000);
            console.log("Got all files successfully...")
            }
            
        )
    }

    const deleteImageApi = (url) =>{
        const URL = `http://localhost:8080/api/delete`
        axios.delete(
            URL,{
                params:{
                    URL: url
                }
            }
        ).then((response)=>{
            console.log(response.data)
            fileDisplay();
        }).catch((error)=>{
            console.log(error)
        }).finally(
            console.log("Deleted Successfully")
        )
    }

    const deleteOption = (URL) =>{
        Swal.fire({
            title: "Do you want to delete the image?",
            showDenyButton: true,
            confirmButtonText: "Delete",
            denyButtonText: `Discard`
          }).then((result) => {
            if (result.isConfirmed) {
              deleteImageApi(URL);
              Swal.fire("Deleted Successfully!", "", "success");
            } else if (result.isDenied) {
              Swal.fire("Image Not Deleted", "", "info");
            }
          });
    }


  return (
    <div>
    <Navigation/>
    {loading ? (
                <div className='loader'>
        <div className='spinner-class'>
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
          </Spinner>
          <p>Loading...</p>
        </div>
                </div>
            ) :
      (<div className='image-proper-display'>
      {
        getFiles.length <=0 ? 
        (<div className='inside-image-proper-display'>
        <TfiFaceSad  className='Emoji'/>
        <p>Nothing to Display. Add Images and comeback</p>
        </div>)
        :
        getFiles.map(img=>(
            <img src={img} alt="" key={img} onClick={()=>{deleteOption(img)}}/>
        ))
      }
      </div>
      )}
    </div>
  )
}

export default ViewImages
