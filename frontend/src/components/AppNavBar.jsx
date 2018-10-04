
import React, { Component } from 'react';
import { Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, NavLink } from 'reactstrap';
import "./AppNavBar.css";

function PortfolioButton() {
  return (
    <NavItem>
    <NavLink href="/user/portfolio">Portfolio</NavLink>
  </NavItem>
  );
}

function LogoutButton(){
  return (
    <NavItem> 
    <NavLink href="/">Logout</NavLink>
    </NavItem>
  )
}
export default class AppNavBar extends Component {
  constructor(props) {
    super(props);

    this.toggleNavbar = this.toggleNavbar.bind(this);
    this.state = {
      collapsed: true
    };
  }



  toggleNavbar() {
    this.setState({
      collapsed: !this.state.collapsed
    });
  }
  render() {
    let portfolioButton, logoutButton;
    const isAuthenticated = this.props.isAuthenticated;
    const currentUser = this.props.currentUser;
    const onLogout = this.props.onLogout;
    if(currentUser && isAuthenticated){
    portfolioButton = <PortfolioButton />;
    logoutButton = <LogoutButton onClick={onLogout}/>

    }

   
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
            
              <NavItem>
              <NavLink href="/stocks">Stocks</NavLink>
            </NavItem>
            {portfolioButton}
              
            {logoutButton}
            </Nav>
          </Collapse>
        </Navbar>
      </div>
    );
  }
}

