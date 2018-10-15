
import React, { Component } from 'react';
import { Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, NavLink } from 'reactstrap';
import "./AppNavBar.css";


// Authorized User navigation. Will only display once a user logs in. 
export default class AuthorizedNav extends Component {
  constructor(props) {
    super(props);

    this.toggleNavbar = this.toggleNavbar.bind(this);
    this.state = {
      collapsed: true, 
      isOpen: false, 
      logout: false
    };
  }
//handles user log out
handleLogout(){
  if(this.state.logout === true){
    this.props.onLogout()
  }
}

//toggle the nav bar
  toggleNavbar() {
    this.setState({
      collapsed: !this.state.collapsed,
      isOpen:!this.state.isOpen
    });
  }
  render() {

    const currentUser = this.props.currentUser;
 
    return (
        <div>
      <Navbar color="light" light expand="md">
    <NavbarBrand href="/">Stock Trade</NavbarBrand>
    <NavbarToggler onClick={this.toggleNavbar} />
    <Collapse isOpen={this.state.isOpen} navbar>
      <Nav className="ml-auto" navbar>
      <NavItem>
    <NavLink href="/stocks">Stocks</NavLink>
    </NavItem>
      <NavItem>
      <NavLink href="user/portfolio">Portfolio</NavLink>
    </NavItem>
    <NavItem>
    <NavLink  href="/user/transactions">Transactions</NavLink>
  </NavItem>
    <NavItem> 
    <NavLink onClick={this.props.onLogout} href="/logout" >Logout</NavLink>
    </NavItem>
      </Nav>
    </Collapse>
   
  </Navbar>
  Hello {currentUser.name}
      </div>
    );
  }
  
}