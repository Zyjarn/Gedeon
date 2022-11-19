import './App.css';
import GedeonHeader from "./gedeon/layout/GedeonHeader"
import GedeonNavBar from './gedeon/layout/GedeonNavBar';
import GedeonTabManager from './gedeon/layout/GedeonTabManager';

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';



function App() {
  return (
    <div className="App">
		<div className="gedeon">
			<header className="header">
				<GedeonHeader />
			</header>
			<aside className="sidebar">
				<GedeonNavBar />
			</aside>
			<main className="content" >
				<GedeonTabManager />
			</main>
			<footer className="footer">
				footer
			</footer>
		</div>
    </div>
  );
}

export default App;
