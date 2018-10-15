
import React, { Component } from 'react';
import { Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, NavLink} from 'reactstrap';
import "./AppNavBar.css";

//Unauthorized component: displays when a user is unauthorized

export default class UnauthorizedNav extends Component {
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
  if(this.state.logout === true){
    this.props.onLogout()
  }
}

  toggleNavbar() {
    this.setState({
      collapsed: !this.state.collapsed
    });
  }
  render() {
   
    return (
        <div>
      <Navbar color="light" light expand="md">
    <NavbarBrand href="/">Stock Trade</NavbarBrand>
    <NavbarToggler onClick={this.toggle} />
    <Collapse isOpen={this.state.isOpen} navbar>
      <Nav className="ml-auto" navbar>
      <NavItem>
   <NavLink href="/login/">Login</NavLink>
 </NavItem>
 <NavItem>
   <NavLink href="/register/">Register</NavLink>
 </NavItem>
      </Nav>
    </Collapse>
  </Navbar>
      </div>
    );
  }
}

