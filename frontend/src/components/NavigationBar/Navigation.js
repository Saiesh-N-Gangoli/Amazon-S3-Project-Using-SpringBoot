import React from 'react'
import image from '../images/img.png'
import './Navigation.css'
import { useNavigate } from 'react-router-dom';

const Navigation = () => {
    const navigate = useNavigate();

    const moveNext = () =>{
        navigate('/view-images')
    }

    const goBack = () =>{
      navigate('/home')
    } 
    
  return (
    <>
      <div className="containers">
        <div className="image-container">
            <img src={image} alt="" />
        </div>
        <div className="select-container">
            <ul className='select-container'>
                <li onClick={goBack}>Home</li>
                <li onClick={moveNext}>View-Images</li>
            </ul>
        </div>
      </div>
    </>
  )
}

export default Navigation
