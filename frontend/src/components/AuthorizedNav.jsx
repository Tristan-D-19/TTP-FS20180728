
import React, { Component } from 'react';
import { Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, NavLink, Button, Col } from 'reactstrap';
import "./AppNavBar.css";



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

handleLogout(){
  if(this.state.logout == true){
    this.props.onLogout()
  }
}

  toggleNavbar() {
    this.setState({
      collapsed: !this.state.collapsed,
      isOpen:!this.state.isOpen
    });
  }
  render() {
    let portfolioButton, logoutButton;
    let navContent;
    const isAuthenticated = this.props.isAuthenticated;
    const currentUser = this.props.currentUser;
   
    // portfolioButton = <PortfolioButton />;
    // logoutButton = <LogoutButton onClick={this.props.onLogout}  />

   
    return (
        <div>
      <Navbar color="light" light expand="md">
    <NavbarBrand href="/">Stock Trade</NavbarBrand>
    <NavbarToggler onClick={this.toggle} />
    <Collapse isOpen={this.state.isOpen} navbar>
      <Nav className="ml-auto" navbar>
      <NavItem>
    <NavLink href="/stocks">Stocks</NavLink>
    </NavItem>
      <NavItem>
      <NavLink href="user/profile">Profile</NavLink>
    </NavItem>
    <NavItem>
    <NavLink  href="/user/portfolio">Portfolio</NavLink>
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