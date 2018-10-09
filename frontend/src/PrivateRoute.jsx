import React from 'react';
import {
    Route,
    Redirect
  } from "react-router-dom";
  
  
// const PrivateRoute = ({ component: Component, authenticated, ...rest }) => (
   
//     <Route
//       {...rest}
//       render={props =>
       
//         authenticated ? (
//           <Component {...rest} {...props} />
//         ) : (
//           <Redirect
//             to={{
//               pathname: '/login',
//               state: { from: props.location }
//             }}
//           />
//         )
//       }
//     />
// );
  



const PrivateRoute = ({ component: Comp, isAuthenticated,currentUser, path,loadProfile, ...rest }) => {
    return (
      <Route
        path={path}
        {...rest}
        render={(props) => 
           isAuthenticated===true? 
            <Comp {...props} {...rest}  />
           : 
            <Redirect to={{ pathname: "/login", state: { from: props.location },}}
            />
          
        }
      />
    );
  };
  

export default PrivateRoute