import React, { Component } from 'react';
// import logo from './logo.svg';
import './App.css';

import Home from './components/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import StockList from './components/StockList';
import AppNavBar from './components/AppNavBar';
import Register from './components/Register';
import Login from './components/Login';
class App extends Component {
  state = {
    isLoading: true,
    groups: []
  };

  
  render() {
    

  

    return (
      <div className="App-container">
      <div className="App-Header"> 
      <AppNavBar> </AppNavBar>
      </div>
      <Router>
      <Switch>
        <Route path='/' exact={true} component={Home}/>
        <Route path='/stocks' exact={true} component={StockList}/>
        <Route exact path="/login/" component={Login} />
          <Route exact path ="/register" component={Register}/>
      </Switch>
    </Router>
    </div>);
  }
}

export default App;