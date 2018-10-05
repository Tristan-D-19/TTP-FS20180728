import React, { Component } from 'react';
// import logo from './logo.svg';
import './App.css';
import { getCurrentUser } from './utils/APIHelper';
import Home from './components/Home';
import { BrowserRouter as Router, Route, Switch, Redirect, withRouter} from 'react-router-dom';
import StockList from './components/StockList';
import AppNavBar from './components/AppNavBar';
import Register from './components/Register';
import Login from './components/Login';
import Profile from './components/Profile';
import PrivateRoute from './PrivateRoute';
import Portfolio from './components/Portfolio';
import NotFound from './components/NotFound';
import {ACCESS_TOKEN} from  './constants';

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false,
      stocks: []
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
   this.handleRegister = this.handleRegister.bind(this);
  }

  handleLogin() {
    this.loadCurrentUser();
    this.props.history.push("/");
    console.log("main");
    if (this.state.isAuthenticated) {
      return <Redirect to="/" />;
    }
  }

  handleRegister(){
    this.props.history.push("/login");
    if (!this.state.isAuthenticated) {
      return <Redirect to="/login" />;
    }
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });

    getCurrentUser()
    .then(response => {
      this.setState({
        currentUser: response,
        isAuthenticated: true,
        isLoading: false
      });
    }).catch(error => {
      this.setState({
        isLoading: false
      });  
    });
  }


  handleLogout(redirectTo="/") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);
    

  }

  componentWillMount() {
    this.loadCurrentUser();
  }
  
  render() {
    
    return (
      <div className="App-container">
            
      <div className="App-Header"> 
      <AppNavBar isAuthenticated={this.state.isAuthenticated} 
            currentUser={this.state.currentUser} 
            onLogout={this.handleLogout}/>
      </div>
      <Switch>
        <Route path='/' exact={true} component={Home}/> 
        <Route exact path="/stocks" 
                  render={(props) => <StockList isAuthenticated={this.state.isAuthenticated} 
                      currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>
        </Route>
        <Route path="/login" 
                  render={(props) => <Login onLogin={this.handleLogin} {...props} />}></Route>

         <Route path="/register" 
                  render={(props) => <Register onRegister={this.handleRegister} {...props} />}></Route>          
        <Route exact path ="/register" component={Register}/>

        <Route path="/users/:id" 
                  render={(props) => <Profile isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}>
        </Route>
        <PrivateRoute authenticated={this.state.isAuthenticated} path="/user/stocks" component={Portfolio}></PrivateRoute>
        <PrivateRoute authenticated={this.state.isAuthenticated} path="/logout" component={Home} handleLogout={this.handleLogout}></PrivateRoute>
                <Route component={NotFound}></Route>      
      </Switch>
    
    </div>);
  }
}

export default withRouter(App);