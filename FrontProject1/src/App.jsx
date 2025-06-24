import { Link, Route, Routes } from 'react-router-dom'
import './App.css'
import Request from './Request'
import Login from './Login'
import Home from './Home'
import MyRequestList from './MyRequestList'
import AllRequestList from './AllRequestList'
import RequestDetail from './RequestDetail'
import EditMember from './EditMember'

function App() {

  return (
      <Routes>
        <Route path="/login" element={<Login/>} />
        <Route path="/" element={<Home/>}/>
        <Route path="/manager" element={<Home/>}/>
        <Route path="/request" element={<Request/>} />
        <Route path="/mypage" element={<MyRequestList/>} />
        <Route path="/mypage/edit" element={<EditMember/>} />
        <Route path="/manager/request" element={<AllRequestList/>} />
        <Route path="/request/:id" element={<RequestDetail/>} />
      </Routes>
  
  )
}

export default App
