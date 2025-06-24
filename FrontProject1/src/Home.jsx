import { useNavigate } from "react-router-dom"

export default function Home() {
    const role = localStorage.getItem("userRole")
    const navi = useNavigate();

    const handleMove = (path) => {
        navi(path);
    }


  return (
   <div className="menu-container">
      <h2 className="menu-title">메인 메뉴</h2>
      <div className="menu-buttons">
        {role === 'ROLE_MEMBER' && (
          <>
            <button className="menu-btn" onClick={() => handleMove('/request')}>분석 요청</button>
            <button className="menu-btn" onClick={() => handleMove('/mypage')}>마이페이지</button>
          </>
        )}
        {role === 'ROLE_MANAGER' && (
          <button className="menu-btn" onClick={() => handleMove('/manager/request')}>요청 확인</button>
        )}
      </div>
    </div>
  );
}
