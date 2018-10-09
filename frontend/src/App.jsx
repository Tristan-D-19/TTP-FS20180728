import React, { Component } from 'react';
// import logo from './logo.svg';
import './App.css';
import { getCurrentUser, getUserProfile } from './utils/APIHelper';
import Home from './components/Home';
import { BrowserRouter as Router, Route, Switch, Redirect, withRouter} from 'react-router-dom';
import StockList from './components/StockList';
import AuthorizedNav from './components/AuthorizedNav';
import UnauthorizeddNav from './components/UnauthorizedNav';
import Register from './components/Register';
import Login from './components/Login';
import Profile from './components/Profile';
import PrivateRoute from './PrivateRoute';
import Portfolio from './components/Portfolio';
import NotFound from './components/NotFound';
import {ACCESS_TOKEN} from  './constants';
import { timeoutsShape } from 'reactstrap';

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
   this.loadUserProfile = this.loadUserProfile.bind(this);
   this.setCurrentUser = this.setCurrentUser.bind(this);
  }

  setCurrentUser(user){
    if(user)
    {this.setState({
      currentUser:user,
      isAuthenticated:true
    })
    }
    else{this.setState({
      currentUser:null,
      isAuthenticated:false
    })
    }
  }

  loadUserProfile(){

    if(this.state.currentUser)
    getUserProfile(this.state.currentUser.id)
    .then(response => {
     this.setState({
       profile:response
     })
      this.setState({profile:response})
  }).catch(error => {
    this.setState({
      isLoading: false
    });  
  })
  }
  handleLogin() {
    this.loadCurrentUser();
    // this.props.history.push("/");
    console.log("main");
    return this.state.currentUser
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
    let nav; 
    const user = this.state.currentUser;
    const authenticated = this.state.isAuthenticated;
    if(this.state.isAuthenticated){
      nav = <AuthorizedNav 
      isAuthenticated={this.state.isAuthenticated} 
            currentUser={this.state.currentUser} 
            onLogout={this.handleLogout}
      ></AuthorizedNav>
      console.log("user", this.state.currentUser);
    }
    else{
      nav = <UnauthorizeddNav></UnauthorizeddNav>
    }
    return (
      <div className="App-container">
            
      <div className="App-Header"> 
      {nav}
      {/* <AppNavBar isAuthenticated={this.state.isAuthenticated} 
            currentUser={this.state.currentUser} 
            onLogout={this.handleLogout}/> */}
      </div>
      <Switch>
        <Route path='/' exact={true} component={Home}/> 
        <PrivateRoute path="/stocks" component={StockList} isAuthenticated={this.state.isAuthenticated}  currentUser={user} handleLogout={this.handleLogout}></PrivateRoute> 
 
        <Route path="/login" 
                  render={(props) => <Login isAuthenticated={this.state.isAuthenticated} onLogin={this.handleLogin} setCurrentUser={this.setCurrentUser} {...props} />}></Route>

         <Route path="/register" 
                  render={(props) => <Register onRegister={this.handleRegister} {...props} />}></Route>          
        <Route exact path ="/register" component={Register}/>

        <PrivateRoute exact path="/user/profile" 
                  component={Profile} loadProfile={this.loadUserProfile} isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} >
        </PrivateRoute>
        <PrivateRoute exact path="/user/portfolio"
        component={(props) => <Portfolio isAuthenticated={authenticated} currentUser={this.state.currentUser} {...props}  />} >
        </PrivateRoute>
        <Route path="/logout" component={Home}></Route>
                <Route component={NotFound}></Route>      
      </Switch>
    
    </div>);
  }
}

export default withRouter(App);