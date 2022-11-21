import './App.css';
import GedeonHeader from "./gedeon/layout/GedeonHeader"
import GedeonNavBar from './gedeon/layout/GedeonNavBar';
import GedeonTabManager from './gedeon/layout/GedeonTabManager2';



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
