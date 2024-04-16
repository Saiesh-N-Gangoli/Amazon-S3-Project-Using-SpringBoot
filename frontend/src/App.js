import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import ImageUploader from './components/ImageUploader';
import ViewImages from './components/View-Images/ViewImages';


function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/home' element={<ImageUploader/>}/>
          <Route path='/view-images' element={<ViewImages/>}/>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
